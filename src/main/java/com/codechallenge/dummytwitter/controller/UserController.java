package com.codechallenge.dummytwitter.controller;

import com.codechallenge.dummytwitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(
        value = UserController.API_URL,
        produces = APPLICATION_JSON_UTF8_VALUE
)
@RequiredArgsConstructor
public class UserController {

    static final String API_URL = "/api/user";
    private final UserService userService;

    @PostMapping(value = "/follow/{followerLogin}/{followedLogin}")
    @ResponseStatus(HttpStatus.CREATED)
    public void follow( @PathVariable String followerLogin, @PathVariable String followedLogin ) {
        userService.follow( followerLogin, followedLogin );
    }
}
