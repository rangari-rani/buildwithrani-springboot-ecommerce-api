package com.buildwithrani.ecommerce.auth.service;

import com.buildwithrani.ecommerce.auth.dto.AuthResponse;
import com.buildwithrani.ecommerce.auth.dto.LoginRequest;
import com.buildwithrani.ecommerce.auth.dto.SignupRequest;
import com.buildwithrani.ecommerce.auth.model.User;
import com.buildwithrani.ecommerce.auth.repository.UserRepository;
import com.buildwithrani.ecommerce.auth.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }



    public AuthResponse signup(SignupRequest request) {

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());

        // 🔐 HASH PASSWORD
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(
                true,
                "Signup successful",
                user.getEmail(),
                token
        );
    }


    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(
                true,
                "Login successful",
                user.getEmail(),
                token
        );
    }
    }


