package com.codechallenge.dummytwitter.controller;

import com.codechallenge.dummytwitter.config.SwaggerImplicitPageableParams;
import com.codechallenge.dummytwitter.dto.MessageDto;
import com.codechallenge.dummytwitter.dto.PostDto;
import com.codechallenge.dummytwitter.entity.Post;
import com.codechallenge.dummytwitter.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(
        value = PostController.API_URL,
        produces = APPLICATION_JSON_UTF8_VALUE
)
@RequiredArgsConstructor
public class PostController {

    static final String API_URL = "/api/post";
    private final PostService postService;

    @PostMapping("/{userLogin}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PostDto> post( @PathVariable String userLogin, @RequestBody @Valid MessageDto messageDto ) {
        final Post savedPost = postService.addPost( userLogin, messageDto );
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentServletMapping()
                        .path( API_URL + "/{id}" )
                        .buildAndExpand( savedPost.getId() )
                        .toUri() )
                .body( new PostDto( savedPost ) );
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PostDto> getPostFromUser( @PathVariable int id ) {
        final Post post = postService.getPostById( id );
        return ResponseEntity.ok( new PostDto( post ) );
    }

    @GetMapping("/self/{userLogin}/")
    @ResponseStatus(HttpStatus.OK)
    @SwaggerImplicitPageableParams
    public Page<PostDto> getPostFromUser( @PathVariable String userLogin, @ApiIgnore Pageable pageable ) {
        return postService.getPostsFromUser( userLogin, pageable );
    }

    @GetMapping("/followed/{userLogin}/")
    @ResponseStatus(HttpStatus.OK)
    @SwaggerImplicitPageableParams
    public Page<PostDto> getPostsFromFollowedUsers( @PathVariable String userLogin, @ApiIgnore Pageable pageable ) {
        return postService.getPostsFromFollowedUsers( userLogin, pageable );
    }
}
