package com.codechallenge.dummytwitter.dto;

import com.codechallenge.dummytwitter.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class PostDto {
    private Integer id;
    private String message;
    private Instant creationDate;
    private String authorLogin;

    public PostDto( Post post ) { //todo add mapper
        this.id = post.getId();
        this.message = post.getMessage();
        this.creationDate = post.getCreatedDate();
        this.authorLogin = post.getAuthor().getLogin();
    }
}
