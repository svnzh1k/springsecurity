package com.example.security.controllers;

import com.example.security.configuration.CustomAuthenticationProvider;
import com.example.security.dtos.UserDTO;
import com.example.security.jwt.JwtService;
import com.example.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final CustomAuthenticationProvider authenticationProvider;

    @Autowired
    public AuthController(UserService userService, JwtService jwtService, CustomAuthenticationProvider authenticationProvider) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationProvider = authenticationProvider;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO userDTO) throws AuthenticationException {
        Authentication authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
        if (authentication.isAuthenticated()){
            System.out.println(authentication.getAuthorities());
            System.out.println(authentication.getPrincipal());
            return jwtService.generateToken(userDTO.getUsername());
        }
        return "No such user";
    }






}
