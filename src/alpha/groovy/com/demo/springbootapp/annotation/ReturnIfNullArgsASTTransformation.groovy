package com.demo.springbootapp.annotation

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.VariableScope
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.syntax.Token
import org.codehaus.groovy.syntax.Types
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

@CompileStatic
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class ReturnIfNullArgsASTTransformation implements ASTTransformation {
    @Override
    void visit(ASTNode[] nodes, SourceUnit source) {
        MethodNode methodNode = nodes[1] as MethodNode
        AnnotationNode annotationNode = nodes[0] as AnnotationNode
        if (!methodNode || !annotationNode) {
            return
        }
        def startMessage = createPrintlnAst("Starting $methodNode.name")
        def endMessage = createPrintlnAst("Ending $methodNode.name")
        def blockStatement = methodNode.code as BlockStatement
        def statements = blockStatement.statements
        def returnStatements = returnStatements(annotationNode, methodNode)

        if (!returnStatements) {
            return
        }

        returnStatements.each { Statement statement ->
            statements.add(0, statement)
        }
        statements.add(0, createPrintlnAst("method node info: " + methodNode.dump()))
        statements.add(0, createPrintlnAst("annotation node info: " + annotationNode.dump()))
        statements.add(0, createPrintlnAst("annotation member info: " + annotationNode.members.dump()))

        statements.add(0, startMessage)
        statements.add(endMessage)
    }

    static List<Statement> returnStatements(AnnotationNode annotationNode, MethodNode methodNode) {
        if (!methodNode.parameters) {
            return null
        }
        List<Statement> statements = []

        methodNode.parameters.each { Parameter parameter ->
            Expression expression = annotationNode.members["removeNullsFromList"] as ConstantExpression

            statements.add(createNullOrEmptyCheck(methodNode, parameter))
            if (expression?.value) {
                Class parameterClass = parameter.type.plainNodeReference.typeClass
                if (parameterClass == List) {
                    statements.add(createRemoveNullsFromList(parameter))
                }
            }
        }
        return statements
    }

    static Statement createRemoveNullsFromList(Parameter parameter) {
        def minusExpression = new BinaryExpression(
                new VariableExpression(parameter.name),
                Token.newSymbol(Types.MINUS, 0, 0),
                new ConstantExpression(null)
        )
        def assignmentExpression = new BinaryExpression(
                new VariableExpression(parameter.name),
                Token.newSymbol(Types.EQUAL, 0,0),
                minusExpression
        )
        def statement = new ExpressionStatement(assignmentExpression)
        return statement
    }

    static Statement createNullOrEmptyCheck(MethodNode methodNode, Parameter parameter) {
        def returnExpression
        switch(methodNode.returnType.plainNodeReference.typeClass) {
            case List:
                returnExpression = new ListExpression()
                break
            case String:
                returnExpression = new ConstantExpression("")
                break
            case Map:
                returnExpression = new MapExpression()
                break
            default:
                returnExpression = new ConstantExpression(null)
                break
        }

        def statement = new IfStatement(
                new BooleanExpression(
                        new NotExpression(
                                new VariableExpression(parameter.name)
                        )
                ),
                new BlockStatement(
                        [
                                new ReturnStatement(returnExpression) as Statement
                        ],
                        new VariableScope()
                ),
                new EmptyStatement()
        )
        return statement
    }

    static Statement createPrintlnAst(String message) {
        new ExpressionStatement(
                new MethodCallExpression(
                        new VariableExpression("this"),
                        new ConstantExpression("println"),
                        new ArgumentListExpression(
                                new ConstantExpression(message)
                        )
                )
        )
    }
}
