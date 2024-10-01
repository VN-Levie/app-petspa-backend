package com.petspa.backend.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.petspa.backend.dto.ApiResponse;
import com.petspa.backend.dto.AuthData;
import com.petspa.backend.dto.LoginDTO;
import com.petspa.backend.dto.RegisterDTO;
import com.petspa.backend.entity.Account;
import com.petspa.backend.repository.AccountRepository;
import com.petspa.backend.security.JwtUtil;
import com.petspa.backend.service.EmailService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private Environment environment;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> authenticateUser(@Valid @RequestBody LoginDTO loginDTO) {

        try {
            String username = loginDTO.getEmail();
            String password = loginDTO.getPassword();
            if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Username or password is empty!",
                        null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            Optional<Account> accountOpt = accountRepository.findByEmail(username);
            if (accountOpt.isEmpty()) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Account does not exist!", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Account account = accountOpt.get();
            if (!passwordEncoder.matches(password, account.getPassword())) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Incorrect password!", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtil.generateToken(account);
            String refreshToken = jwtUtil.generateToken(username, "refresh_token");
            AuthData data = new AuthData(account, jwt, refreshToken);
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Login successful", data);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Username or password is empty!",
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody RegisterDTO registerDTO) {
        try {
            if (accountRepository.existsByEmail(registerDTO.getEmail())) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Email is already registered!",
                        null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (!registerDTO.getPassword().equals(registerDTO.getRePassword())) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Passwords do not match!", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Account account = new Account();
            account.setRoles("ROLE_USER");
            account.setEmail(registerDTO.getEmail().toLowerCase());
            account.setName(registerDTO.getName());
            account.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            account.setAddressBooks(List.of());
            account.setCreatedAt(LocalDateTime.now());
            accountRepository.save(account);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(registerDTO.getEmail(), registerDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtil.generateToken(account);
            String refreshToken = jwtUtil.generateToken(registerDTO.getEmail(), "refresh_token");
            AuthData data = new AuthData(account, jwt, refreshToken);
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_CREATED,
                    "User registered and logged in successfully", data);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Plesy fill all field!",
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/verify-token")
    public ResponseEntity<ApiResponse> verifyToken(String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            if (jwtUtil.validateToken(token, "access_token")) {
                String username = jwtUtil.getUsernameFromToken(token);
                Optional<Account> accountOpt = accountRepository.findByEmail(username);
                if (accountOpt.isEmpty()) {
                    ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Account does not exist!",
                            null);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }

                Account account = accountOpt.get();
                // gen luôn token mới
                String newToken = jwtUtil.generateToken(account);
                AuthData data = new AuthData(account, newToken, null);
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Token is valid", data);
                return ResponseEntity.ok(response);
            } else {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_UNAUTHORIZED, "Invalid token", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_UNAUTHORIZED, "Invalid token", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    // lấy toekm mới từ refresh token
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse> refreshToken(@RequestParam String refreshToken) {
        try {
            if (refreshToken.startsWith("Bearer ")) {
                refreshToken = refreshToken.substring(7);
            }

            if (!jwtUtil.validateToken(refreshToken, "refresh_token")) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_UNAUTHORIZED, "Invalid refresh token", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            String username = jwtUtil.getUsernameFromToken(refreshToken);
            Optional<Account> accountOpt = accountRepository.findByEmail(username);
            if (accountOpt.isEmpty()) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Account does not exist!", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Account account = accountOpt.get();
            String jwt = jwtUtil.generateToken(account);
            AuthData data = new AuthData(account, jwt, refreshToken);
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Token refreshed successfully", data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_UNAUTHORIZED, "Invalid refresh token", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestParam String email) {
        String port = environment.getProperty("server.port");
        Optional<Account> accountOpt = accountRepository.findByEmail(email);

        if (accountOpt.isEmpty()) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Email does not exist!", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        String resetToken = jwtUtil.generateToken(email, "reset_password");
        String resetLink = "http://localhost:" + port + "/reset-password?token=" + resetToken;
        emailService.sendEmail(email, "Reset Password", "Click here to reset your password: " + resetLink);

        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Password reset link sent successfully.", null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        if (!jwtUtil.validateToken(token, "reset_password")) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_UNAUTHORIZED, "Invalid or expired token", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String email = jwtUtil.getUsernameFromToken(token);
        Optional<Account> accountOpt = accountRepository.findByEmail(email);

        if (accountOpt.isEmpty()) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Email does not exist!", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Account account = accountOpt.get();
        account.setPassword(passwordEncoder.encode(newPassword));
        accountRepository.save(account);

        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Password reset successfully.", null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse> changePassword(@RequestParam String currentPassword,
            @RequestParam String newPassword) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Account> accountOpt = accountRepository.findByEmail(email);

        if (accountOpt.isEmpty()) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Account does not exist!", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Account account = accountOpt.get();
        if (!passwordEncoder.matches(currentPassword, account.getPassword())) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Current password is incorrect!",
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        account.setPassword(passwordEncoder.encode(newPassword));
        accountRepository.save(account);

        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Password changed successfully.", null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/send")
    public ResponseEntity<String> sendTestEmail(@RequestParam String toEmail) {
        try {
            emailService.sendEmail(toEmail, "Test Email", "This is a test email from Pet Spa application.");
            return ResponseEntity.ok("Email sent successfully to " + toEmail);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send email: " + e.getMessage());
        }
    }

}
