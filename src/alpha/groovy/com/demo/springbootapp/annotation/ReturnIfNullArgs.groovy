package com.demo.springbootapp.annotation

import org.codehaus.groovy.transform.GroovyASTTransformationClass

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Retention(RetentionPolicy.SOURCE)
@Target([ElementType.METHOD])
@GroovyASTTransformationClass(["com.demo.springbootapp.annotation.ReturnIfNullArgsASTTransformation"])
@interface ReturnIfNullArgs {
    boolean removeNullsFromList() default false
}