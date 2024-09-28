package com.petspa.backend.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.petspa.backend.dto.ApiResponse;
import com.petspa.backend.dto.LoginDTO;
import com.petspa.backend.dto.RegisterDTO;
import com.petspa.backend.entity.Account;
import com.petspa.backend.repository.AccountRepository;
import com.petspa.backend.security.JwtUtil;
import com.petspa.backend.service.EmailService;

import jakarta.validation.Valid;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    // Đăng nhập
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> authenticateUser(@Valid @RequestBody LoginDTO loginDTO) {
        System.out.println("có chạy vào đây...............");
        try {
            String username = loginDTO.getEmail();
            String password = loginDTO.getPassword();
            //in ra tk, mk để test
            System.out.println("username: " + username + " pass: " + password);
            // check null | empty
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
            String jwt = jwtUtil.generateToken(username);

            ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Login successful", jwt);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Username or password is empty!",
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Đăng ký tài khoản và trả về token
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody RegisterDTO registerDTO) {
        try {
            // Kiểm tra xem username đã tồn tại hay chưa
            if (accountRepository.existsByEmail(registerDTO.getEmail())) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Email is already registered!",
                        null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
    
            // Kiểm tra xem mật khẩu và mật khẩu nhập lại có khớp không
            if (!registerDTO.getPassword().equals(registerDTO.getRePassword())) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Passwords do not match!", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
    
            // Tạo tài khoản mới
            Account account = new Account();
            account.setEmail(registerDTO.getEmail());
            account.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
    
            // Đảm bảo danh sách địa chỉ ban đầu là trống (nếu không được cung cấp)
            account.setAddressBooks(List.of());
    
            // Lưu account vào cơ sở dữ liệu
            accountRepository.save(account);
    
            // Đăng nhập tự động sau khi đăng ký
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(registerDTO.getEmail(), registerDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtil.generateToken(registerDTO.getEmail());
    
            // Trả về JWT cùng với thông báo đăng ký thành công
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_CREATED, "User registered and logged in successfully",
                    jwt);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Plesy fill all field!",
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/verify-token")
    public ResponseEntity<String> verifyToken(@RequestHeader("Authorization") String token) {
        try {
            // Kiểm tra tính hợp lệ của JWT
            if (jwtUtil.validateToken(token.substring(7))) {
                return ResponseEntity.ok("Token is valid");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    private static final String CLIENT_ID = "674239811909-m2eeg61cbpnmr7q69n522ajndm7lkqhu.apps.googleusercontent.com"; // Google
                                                                                                                        // Client
                                                                                                                        // ID

    // Đăng nhập bằng Google
    @PostMapping("/google-signin")
    public ResponseEntity<ApiResponse> googleSignIn(@RequestBody Map<String, String> request) {
        String idTokenString = request.get("idToken");

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
                GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Lấy email của người dùng từ payload
                String email = payload.getEmail();
                String name = (String) payload.get("name");

                // Kiểm tra xem tài khoản đã tồn tại chưa
                // Kiểm tra xem tài khoản đã tồn tại chưa
                Optional<Account> optionalAccount = accountRepository.findByEmail(email);
                if (optionalAccount.isPresent()) {
                    // Nếu tài khoản đã tồn tại
                    Account account = optionalAccount.get();
                    // kiểm tra nếu có token thì cập nhật token
                    accountRepository.save(account);

                } else {
                    // Nếu tài khoản không tồn tại, tạo tài khoản mới
                    Account account = new Account();
                    account.setEmail(email);
                    account.setPassword(passwordEncoder.encode("google_auth_password")); // Đặt mật khẩu ngẫu nhiên
                    account.setName(name);
                    accountRepository.save(account);
                }

                // Tạo JWT token
                String jwt = jwtUtil.generateToken(email);

                ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Google Sign-In successful", jwt);
                return ResponseEntity.ok(response);

            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse(ApiResponse.STATUS_UNAUTHORIZED, "Invalid Google ID Token", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(ApiResponse.STATUS_UNAUTHORIZED, "Token verification failed", null));
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

        // Tạo mã reset token hoặc link reset
        String resetToken = jwtUtil.generateToken(email); // Hoặc tạo một token riêng
        // Gửi email cho người dùng với link đặt lại mật khẩu
        String resetLink = "http://localhost:" + port + "/reset-password?token=" + resetToken;
        emailService.sendEmail(email, "Reset Password", "Click here to reset your password: " + resetLink);

        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Password reset link sent successfully.", null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        if (!jwtUtil.validateToken(token)) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_UNAUTHORIZED, "Invalid or expired token", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // Lấy email từ token
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
        // Gửi email test
        try {
            emailService.sendEmail(toEmail, "Test Email", "This is a test email from Pet Spa application.");
            return ResponseEntity.ok("Email sent successfully to " + toEmail);
        } catch (Exception e) {
            // e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to send email: " + e.getMessage());
        }
    }

}
