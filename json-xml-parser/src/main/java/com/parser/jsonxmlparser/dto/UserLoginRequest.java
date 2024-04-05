package com.parser.jsonxmlparser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserLoginRequest {
    private String userName;
    private String password;
}
