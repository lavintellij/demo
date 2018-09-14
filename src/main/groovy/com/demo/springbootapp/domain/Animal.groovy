package com.demo.springbootapp.domain

import com.demo.springbootapp.annotation.ReturnIfNullArgs
import groovy.transform.EqualsAndHashCode
import groovy.util.logging.Slf4j

@EqualsAndHashCode
@Slf4j
class Animal {
    String type

    String name

    @ReturnIfNullArgs(removeNullsFromList = true)
    List determineTypeName(String stringToPrint, List<String> strings) {
        return [type, name] + strings
    }

    @ReturnIfNullArgs
    String typeName(String input) {
        return "$type:$name:$input"
    }

    @ReturnIfNullArgs
    def static randomStuff(String huh) {
        return "huh"
    }

    @ReturnIfNullArgs
    def static anotherMethod(String huh = "alpha") {}
}
