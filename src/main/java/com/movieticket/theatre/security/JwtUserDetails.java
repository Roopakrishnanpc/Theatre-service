package com.movieticket.theatre.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtUserDetails {

    private String username;
    private String role;
}