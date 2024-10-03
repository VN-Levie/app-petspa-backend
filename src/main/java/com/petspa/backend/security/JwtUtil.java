package com.petspa.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import com.petspa.backend.entity.Account;

import java.text.DateFormat;
import java.util.Date;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    private String secret_key = "50ae2cfcf2c0a1eec114dc8b9283a27bf7b96b9379145997e666ad74f95ae67f5521f95472f52b380f0a1df3564af7f8a3809243c4103f4a87bd7653de5e02db11b1f2fbb667dfd9322e445c392ea3d96282ab3022c942ea41c4af29be8d2a1ca81120122a73f7e159561be5d7811cb452ae9016bae7237f7438714ae265be2e75578a373d29c6c7b7c0523c67bdbd87bc1ff1165cc003f45b9418db98be4ecf1e7f1c730e00a76578c6eb5227bd6e5234ed2449aae249040d4ec9580ecd33dd6ccb30d60e5509ba03a5052220c1bcd3c356447608da71865dbd358838f3996086d1b0b28f3917820f7c9899927258587ad892bfba3dc9ca18ee16e701404ad1";
    // generate secret from secret_key
    private SecretKey secret = Keys.hmacShaKeyFor(secret_key.getBytes());
    // private SecretKey secret = Keys.secretKeyFor(SignatureAlgorithm.HS256); //
    // Tạo key mạnh hơn
    private long jwtExpirationInMs = 3600000; // 1 hour
    // thời gian sống của refresh token là 90 ngày
    private long refreshTokenExpirationInMs = jwtExpirationInMs * 24 * 90; // 90 ngày :
    private long otpExpirationInMs = 600000; // 10 minutes

    public String generateToken(String username, String type) {
        Date now = new Date();
        Date expiryDate = switch (type) {
            case "access_token" -> new Date(now.getTime() + jwtExpirationInMs);
            case "refresh_token" -> new Date(now.getTime() + refreshTokenExpirationInMs);
            case "otp" -> new Date(now.getTime() + otpExpirationInMs);
            default -> new Date(now.getTime() + otpExpirationInMs);
        };
        // tets show thời gian hếthạn dd/MM/yyyy HH:mm:ss
        System.out.println("Thời gian hết hạn: " + DateFormat.getDateInstance(DateFormat.LONG).format(expiryDate));
        return Jwts.builder()
                .setSubject(username)
                .claim("type", type)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + refreshTokenExpirationInMs))
                .signWith(secret)
                .compact();
    }

    public String generateToken(Account account) {
        // thêmcác thông tin email, ngày tạo tài khoản, name
        return Jwts.builder()
                .setSubject(account.getEmail())
                .claim("name", account.getName())                
                .claim("type", "access_token")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationInMs))
                .signWith(secret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token, String requiredType) {
        try {
            var claims = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
            String tokenType = claims.get("type", String.class);
            System.out.println("Token type: " + tokenType);
            if (!requiredType.equals(tokenType)) {
                return false;
            }

            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            throw ex;
        }

    }

}
