package com.codechallenge.dummytwitter.service;

import com.codechallenge.dummytwitter.dto.MessageDto;
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
    public Page<Post> getPostsFromFollowedUsers( String login, Pageable pageable ) {
        final User user = userService.findByLogin( login );
        return postRepository.findAllByAuthorInOrderByCreatedDateDesc( user.getFollowedUsers(), pageable );
    }

    @Transactional(readOnly = true)
    public Page<Post> getPostsFromUser( String login, Pageable pageable ) {
        final User user = userService.findByLogin( login );
        return postRepository.findAllByAuthorOrderByCreatedDateDesc( user, pageable );
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
