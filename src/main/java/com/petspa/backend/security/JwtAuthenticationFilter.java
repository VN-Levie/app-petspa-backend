package com.petspa.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Lấy JWT từ header
        String jwtToken = getJwtFromRequest(request);
        boolean isValidate = false;
        try {
            isValidate = jwtUtil.validateToken(jwtToken, "access_token");
        } catch (Exception e) {

        }
        // Lấy username từ token
        if (jwtToken != null && isValidate) {
            String username = jwtUtil.getUsernameFromToken(jwtToken);

            // Lấy UserDetails từ UserDetailsService và kiểm tra xác thực
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Nếu token hợp lệ, thiết lập người dùng được xác thực
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Tiếp tục thực hiện các filter khác trong chuỗi
        filterChain.doFilter(request, response);
    }

    // Hàm lấy JWT từ header Authorization
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
