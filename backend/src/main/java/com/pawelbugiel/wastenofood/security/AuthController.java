package com.pawelbugiel.wastenofood.security;

import com.pawelbugiel.wastenofood.security.dtos.AuthRequest;
import com.pawelbugiel.wastenofood.security.dtos.AuthResponse;
import com.pawelbugiel.wastenofood.security.dtos.RegisterRequest;
import com.pawelbugiel.wastenofood.security.dtos.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register-enduser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthResponse> registerEnduser(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registerEnduser(request));
    }

    @PostMapping("/register-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthResponse> registerAdmin(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registerAdmin(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @DeleteMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        authService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @PageableDefault(
                    page = 0,
                    sort = "name",
                    direction = Sort.Direction.ASC)
            Pageable pageable) {
        return ResponseEntity.ok(authService.getAllUsers(pageable));
    }
}