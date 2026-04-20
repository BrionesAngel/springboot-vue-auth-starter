package com.example.backend.auth.service;

import lombok.RequiredArgsConstructor;
import com.example.backend.users.User;
import com.example.backend.users.UserService;
import com.example.backend.auth.security.JwtService;
import com.example.backend.auth.dto.AuthResponse;
import com.example.backend.auth.dto.RegisterRequest;
import com.example.backend.auth.dto.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        UserDetails user = userDetailsService.loadUserByUsername(request.email());
        String token = jwtService.generateToken(user.getUsername());

        return new AuthResponse(token);
    }

    public AuthResponse register(RegisterRequest request) {

        User user = userService.createUser(request);
        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);
    }
}
