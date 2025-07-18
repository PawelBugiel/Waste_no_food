package com.pawelbugiel.wastenofood.security;

import com.pawelbugiel.wastenofood.security.dtos.AuthRequest;
import com.pawelbugiel.wastenofood.security.dtos.AuthResponse;
import com.pawelbugiel.wastenofood.security.dtos.RegisterRequest;
import com.pawelbugiel.wastenofood.security.dtos.UserResponse;
import com.pawelbugiel.wastenofood.security.jwt.JwtService;
import com.pawelbugiel.wastenofood.security.models.Role;
import com.pawelbugiel.wastenofood.security.models.User;
import com.pawelbugiel.wastenofood.security.repositories.RoleRepository;
import com.pawelbugiel.wastenofood.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final UserMapper userMapper;

    public AuthResponse registerEnduser(RegisterRequest request) {
        Role enduserRole = roleRepository.findByName("ROLE_ENDUSER")
                .orElseThrow(() -> new IllegalStateException("Default role not found: ROLE_ENDUSER"));
        return createAndSaveUser(request, enduserRole);
    }

    public AuthResponse registerAdmin(RegisterRequest request) {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new IllegalStateException("Default role not found: ROLE_ADMIN"));
        return createAndSaveUser(request, adminRole);
    }

    private AuthResponse createAndSaveUser(RegisterRequest request, Role role) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(role));

        User savedUser = userRepository.save(user);
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", role.getName().replace("ROLE_", "")); // Dodajemy rolę jako claim
        String jwt = jwtService.generateToken(extraClaims, new CustomUserDetails(savedUser));
        return new AuthResponse(jwt);
    }

    public AuthResponse authenticate(AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getEmail()));

        UserDetails userDetails = new CustomUserDetails(user);
        // Pobieramy rolę użytkownika i dodajemy jako claim
        String role = user.getRoles().stream()
                .map(Role::getName)
                .map(name -> name.replace("ROLE_", ""))
                .findFirst()
                .orElse("ENDUSER"); // Domyślna rola, jeśli brak
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", role);

        String jwt = jwtService.generateToken(extraClaims, userDetails);
        return new AuthResponse(jwt);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));
        userRepository.delete(user);
    }

    public Page<UserResponse> getAllUsers(Pageable pageable) {

        return userRepository.findAll(pageable)
                .map(userMapper::toUserResponse);
    }
}