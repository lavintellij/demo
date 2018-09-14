package com.demo.springbootapp.services

import com.demo.springbootapp.domain.Dog
import spock.lang.Specification
import spock.lang.Unroll

class AnimalSpec extends Specification {
    @Unroll
    def "determineTypeName - #desc"() {
        given:
        Dog dog = new Dog(type: "dog", name: "Fido")

        when:
        def result = dog.determineTypeName(type, animalList)

        then:
        result == expectedResult

        where:
        desc                   | type  | animalList         || expectedResult
        "with values"          | "dog" | ["alpha", "bravo"] || ["dog", "Fido", "alpha", "bravo"]
        "with null type"       | null  | ["alpha", "bravo"] || []
        "with empty list"      | "dog" | []                 || []
        "with empty null list" | "dog" | [null]             || []
        "with null in list"    | "dog" | ["alpha", null]    || ["dog", "Fido", "alpha"]
    }

    @Unroll
    def "string method test"() {
        given:
        Dog dog = new Dog(type: "dog", name: "Fido")

        when:
        def result = dog.typeName(input)

        then:
        result == expectedResult

        where:
        desc    | input | expectedResult
        "name"  | "a"   | "dog:Fido:a"
        "empty" | ""    | ""
    }

    @Unroll
    def "huh test - #desc"() {
        given:
        Dog dog = new Dog(type: "dog", name: "Fido")

        when:
        def result = dog.randomStuff(input)

        then:
        result == expectedResult

        where:
        desc    | input | expectedResult
        "name"  | "a"   | "huh"
        "empty" | ""    | null
    }

    def "anotherMethod test"() {
        given:
        Dog dog = new Dog(type: "dog", name: "Fido")

        when:
        def result = dog.anotherMethod()

        then:
        !result
    }
}
