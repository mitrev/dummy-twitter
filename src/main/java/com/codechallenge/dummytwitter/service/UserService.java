package com.codechallenge.dummytwitter.service;

import com.codechallenge.dummytwitter.entity.User;
import com.codechallenge.dummytwitter.exception.ResourceNotFoundException;
import com.codechallenge.dummytwitter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void follow( String followerLogin, String followedLogin ) {
        final User followerUser = userRepository.findByLogin( followerLogin ).orElseThrow( ResourceNotFoundException::new );
        final User followedUser = userRepository.findByLogin( followedLogin ).orElseThrow( ResourceNotFoundException::new );

        followerUser.addFollowedUser( followedUser );
    }

    private User createUser( String login ) {
        return userRepository.save( new User().withLogin( login ) );
    }

    public User getOrCreateUser( String login ) {
        return userRepository.findByLogin( login ).orElseGet( () -> createUser( login ) );
    }

    public User findByLogin( String login ) {
        return userRepository.findByLogin( login ).orElseThrow( ResourceNotFoundException::new );
    }
}
