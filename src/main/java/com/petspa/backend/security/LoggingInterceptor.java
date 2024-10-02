package com.petspa.backend.security;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    // Mã ANSI cho màu sắc
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_WHITE = "\u001B[37m";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String fullUrl = request.getRequestURL().toString();
       

        // Lấy thời gian hiện tại và định dạng
        String method = request.getMethod();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm:ss");
        String time = LocalDateTime.now().format(formatter);

        //in ra toàn bộ thông tin của request
   

        // In ra URL chính và phương thức được gọi
        System.out.println(ANSI_GREEN + "[" + time + "]" + ANSI_RESET + " " + ANSI_YELLOW + method + ANSI_RESET + " "
                + ANSI_CYAN + fullUrl + ANSI_RESET);
              
        return true; // Tiếp tục cho phép xử lý request
    }

}
