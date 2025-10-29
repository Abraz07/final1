package com.rwtool.controller;

import com.rwtool.dto.AuthResponse;
import com.rwtool.dto.LoginRequest;
import com.rwtool.dto.SignupRequest;
import com.rwtool.service.AuthService;
import com.rwtool.service.UserActivityLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private UserActivityLogService auditLogService;
    
    @Operation(summary = "Register a new user", description = "Create a new user account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input or email already exists")
    })
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest request) {
        try {
            AuthResponse response = authService.signup(request);
            // Log successful signup
            auditLogService.logSuccess(
                request.getEmail(),
                request.getFullName(),
                request.getRole(),
                "USER_SIGNUP",
                "User signed up successfully"
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            // Log failed signup
            auditLogService.logFailure(
                request.getEmail(),
                request.getFullName(),
                request.getRole(),
                "USER_SIGNUP",
                "Signup failed: " + e.getMessage()
            );
            throw e;
        }
    }
    
    @Operation(summary = "Login user", description = "Authenticate user and return JWT token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            // Log successful login
            auditLogService.logSuccess(
                response.getEmail(),
                response.getFullName(),
                response.getRole(),
                "USER_LOGIN",
                "User logged in successfully"
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Log failed login
            auditLogService.logFailure(
                request.getEmail(),
                "Unknown",
                "Unknown",
                "LOGIN_FAILED",
                "Failed login attempt: " + e.getMessage()
            );
            throw e;
        }
    }
    
    @Operation(summary = "Health check", description = "Check if the authentication service is running")
    @ApiResponse(responseCode = "200", description = "Service is healthy")
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Auth service is running");
    }
}
