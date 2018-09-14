package com.demo.springbootapp.services

import spock.lang.Specification

class TestSpec extends Specification{
    def "test"() {
        when:
        String test = "bob"

        then:
        test
    }
}
