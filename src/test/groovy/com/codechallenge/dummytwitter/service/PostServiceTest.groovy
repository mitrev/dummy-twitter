package com.codechallenge.dummytwitter.service

import com.codechallenge.dummytwitter.dto.MessageDto
import com.codechallenge.dummytwitter.entity.Post
import com.codechallenge.dummytwitter.entity.User
import com.codechallenge.dummytwitter.repository.PostRepository
import spock.lang.Specification

class PostServiceTest extends Specification {

    UserService userService
    PostRepository postRepository
    PostService postService

    void setup() {
        userService = Mock( UserService )
        postRepository = Mock( PostRepository )

        postService = new PostService( userService, postRepository )
    }

    def "AddPost should create a post and return"() {
        given:
        def userLogin = "userLogin"
        def user = new User().withLogin( userLogin )

        def messageDto = new MessageDto();
        messageDto.setMessage( "Not too long message" )

        userService.getOrCreateUser( _ as String ) >> user
        postRepository.save( _ as Post ) >> { Post post -> post }

        when:
        def savedPost = postService.addPost( userLogin, messageDto )

        then:
        savedPost.getAuthor() == user
        savedPost.getMessage() == messageDto.getMessage()

    }
}
