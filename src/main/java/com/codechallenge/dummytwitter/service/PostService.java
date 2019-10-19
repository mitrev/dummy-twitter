package com.codechallenge.dummytwitter.service;

import com.codechallenge.dummytwitter.dto.MessageDto;
import com.codechallenge.dummytwitter.dto.PostDto;
import com.codechallenge.dummytwitter.entity.Post;
import com.codechallenge.dummytwitter.entity.User;
import com.codechallenge.dummytwitter.exception.ResourceNotFoundException;
import com.codechallenge.dummytwitter.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserService userService;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public Page<PostDto> getPostsFromFollowedUsers( String login, Pageable pageable ) {
        final User user = userService.findByLogin( login );

        final Page<Post> posts = postRepository.findAllByAuthorInOrderByCreatedDateDesc( user.getFollowedUsers(), pageable );
        return posts.map( PostDto::new );
    }

    @Transactional(readOnly = true)
    public Page<PostDto> getPostsFromUser( String login, Pageable pageable ) {
        final User user = userService.findByLogin( login );

        final Page<Post> posts = postRepository.findAllByAuthorOrderByCreatedDateDesc( user, pageable );
        return posts.map( PostDto::new );
    }

    @Transactional
    public Post addPost( String login, @Valid MessageDto messageDto ) {
        final User user = userService.getOrCreateUser( login );

        Post post = new Post();
        post.setAuthor( user );
        post.setMessage( messageDto.getMessage() );

        return postRepository.save( post );
    }

    @Transactional(readOnly = true)
    public Post getPostById( int id ) {
        return postRepository.findById( id ).orElseThrow( ResourceNotFoundException::new );
    }
}
