package com.codechallenge.dummytwitter.service

import spock.lang.Specification

class UserServiceTest extends Specification {
    def "Follow"() {
        given:
        def var1 = 1
        def var2 = 2

        when:
        def result = var1 + var2
        then:
        result == 3
    }
}
