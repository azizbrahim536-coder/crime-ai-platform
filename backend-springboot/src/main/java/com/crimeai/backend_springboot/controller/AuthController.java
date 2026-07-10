package com.crimeai.backend_springboot.controller;

import com.crimeai.backend_springboot.dto.AuthResponse;
import com.crimeai.backend_springboot.dto.LoginRequest;
import com.crimeai.backend_springboot.dto.RegisterRequest;
import com.crimeai.backend_springboot.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}