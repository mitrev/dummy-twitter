package com.codechallenge.dummytwitter.service

import com.codechallenge.dummytwitter.entity.User
import com.codechallenge.dummytwitter.exception.ResourceNotFoundException
import com.codechallenge.dummytwitter.repository.UserRepository
import spock.lang.Specification

class UserServiceTest extends Specification {

    UserRepository repository
    UserService service

    void setup() {
        repository = Mock( UserRepository )
        service = new UserService( repository )
    }

    def "Should not create new user if user already exists"() {
        given:
        def userLogin = "user"
        def user = new User().withLogin( userLogin )

        repository.findByLogin( userLogin ) >> Optional.of( user )

        when:
        def result = service.getOrCreateUser( userLogin )

        then:
        result == user
        0 * repository.save( _ as User )
    }

    def "Should create new user if user doesn't exist"() {
        given:
        def userLogin = "user"
        def user = new User().withLogin( userLogin )

        repository.findByLogin( userLogin ) >> Optional.empty()

        when:
        service.getOrCreateUser( userLogin )

        then:
        1 * repository.save( user )
    }

    def "Should throw if user with login doesn't exist"() {
        given:
        def userLogin = "user"
        repository.findByLogin( userLogin ) >> Optional.empty()

        when:
        service.findByLogin( userLogin )

        then:
        thrown ResourceNotFoundException
    }

}
