package com.codechallenge.dummytwitter.controller

import com.codechallenge.dummytwitter.dto.MessageDto
import com.codechallenge.dummytwitter.entity.Post
import com.codechallenge.dummytwitter.entity.User
import com.codechallenge.dummytwitter.service.PostService
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.base.Strings
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.Instant

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@WebMvcTest(PostController)
@Import(ObjectMapper)
class PostControllerTest extends Specification {
    @Autowired
    MockMvc mvc
    @Autowired
    ObjectMapper mapper
    @Autowired
    PostService postService

    def "Should throw if message too long"() {
        given:
        def message = new MessageDto()
        message.setMessage( Strings.repeat( "a", 200 ) )

        def json = mapper.writeValueAsString( message )

        when:
        def result = mvc.perform(
                post( "/api/post/user1" )
                        .content( json )
                        .contentType( MediaType.APPLICATION_JSON_UTF8_VALUE )
                        .accept( MediaType.APPLICATION_JSON_UTF8_VALUE ) )
                .andReturn()

        then:
        result.response.status == HttpStatus.BAD_REQUEST.value()
    }


    def "Should return HTTP CREATED if post gets written"() {
        given:
        def message = new MessageDto()
        message.setMessage( Strings.repeat( "a", 1 ) )

        def json = mapper.writeValueAsString( message )
        postService.addPost( _ as String, _ as MessageDto ) >> getPost()

        when:
        def result = mvc.perform(
                post( "/api/post/user1" )
                        .content( json )
                        .contentType( MediaType.APPLICATION_JSON_UTF8_VALUE )
                        .accept( MediaType.APPLICATION_JSON_UTF8_VALUE ) )
                .andReturn()

        then:
        result.response.status == HttpStatus.CREATED.value()
    }

    def "Should return HTTP OK if getPostFromUser doesn't throw"() {
        given:
        postService.getPostsFromUser( _ as String, _ as Pageable ) >> new PageImpl<Post>( List.of( getPost() ) )

        when:
        def result = mvc.perform(
                get( "/api/post/self/user1" ) )
                .andReturn()

        then:
        result.response.status == HttpStatus.OK.value()
    }

    def "Should return HTTP OK if getPostsFromFollowedUsers doesn't throw"() {
        given:
        postService.getPostsFromFollowedUsers( _ as String, _ as Pageable ) >> new PageImpl<Post>( List.of( getPost() ) )

        when:
        def result = mvc.perform(
                get( "/api/post/followed/user1" ) )
                .andReturn()

        then:
        result.response.status == HttpStatus.OK.value()
    }

    private static Post getPost() {
        Post post = new Post()
        post.setId( 1 )
        post.setMessage( "message" )
        post.setAuthor( new User().withLogin( "userLogin" ) )
        post.setCreatedDate( Instant.now() )
        return post;
    }

    @TestConfiguration
    static class MockConfig {
        def factory = new DetachedMockFactory()

        @Bean
        PostService getPostService() {
            return factory.Mock( PostService )
        }

    }
}
