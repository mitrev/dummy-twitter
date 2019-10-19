package com.codechallenge.dummytwitter.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class MessageDto {
    @Length(max = 140)
    private String message;
}
