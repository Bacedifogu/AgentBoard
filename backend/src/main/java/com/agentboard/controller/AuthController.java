package com.agentboard.controller;

import com.agentboard.dto.AuthResponse;
import com.agentboard.dto.LoginRequest;
import com.agentboard.dto.RegisterRequest;
import com.agentboard.model.User;
import com.agentboard.security.CustomUserDetailsService;
import com.agentboard.security.JwtService;
import com.agentboard.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        User user = userService.register(request);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtService.generateToken(userDetails);

        AuthResponse response = AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .userId(user.getId())
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        User user = userService.findByUsername(request.getUsername());

        String token = jwtService.generateToken(userDetails);

        AuthResponse response = AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .userId(user.getId())
                .build();

        return ResponseEntity.ok(response);
    }
}
