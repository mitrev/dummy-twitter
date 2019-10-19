package com.codechallenge.dummytwitter.repository;

import com.codechallenge.dummytwitter.entity.Post;
import com.codechallenge.dummytwitter.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * Repository for retrieving Posts from the database.
 *
 * The Pageable sort parameter is ignored so that the entities are always ordered by creation_date descending
 */

public interface PostRepository extends JpaRepository<Post, Integer> {

    Page<Post> findAllByAuthorOrderByCreatedDateDesc( User author, Pageable pageable );

    Page<Post> findAllByAuthorInOrderByCreatedDateDesc( Set<User> authors, Pageable pageable );

}
