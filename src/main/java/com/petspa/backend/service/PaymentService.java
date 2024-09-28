package com.petspa.backend.service;

import org.springframework.stereotype.Service;

import com.petspa.backend.dto.PaymentOTP;

@Service
public class PaymentService {
    public String generatePaymentUrl(PaymentOTP request) {
        // Tạo URL thanh toán và trả về cho controller
        String tmnCode = "vnp_TmnCode"; // Mã được VNPAY cung cấp
        String returnUrl = request.getReturnUrl();
        // logic tạo URL với các tham số cần thiết
        // return paymentUrl;
        return "";
    }
}

