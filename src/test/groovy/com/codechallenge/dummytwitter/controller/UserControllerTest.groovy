package com.codechallenge.dummytwitter.controller

import com.codechallenge.dummytwitter.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@WebMvcTest(UserController)
class UserControllerTest extends Specification {
    @Autowired
    MockMvc mvc
    @Autowired
    UserService userService

    def "Should return HTTP CREATED if service doesn't throw exception"() {
        when:
        def result = mvc.perform(
                post( "/api/user/follow/user1/user2" )
                        .contentType( MediaType.APPLICATION_JSON_UTF8_VALUE )
                        .accept( MediaType.APPLICATION_JSON_UTF8_VALUE ) )
                .andReturn()

        then:
        result.response.status == HttpStatus.CREATED.value()
    }

    @TestConfiguration
    static class MockConfig {
        def factory = new DetachedMockFactory()

        @Bean
        UserService getUserService() {
            return factory.Mock( UserService )
        }

    }
}
