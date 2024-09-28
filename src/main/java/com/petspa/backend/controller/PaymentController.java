package com.petspa.backend.controller;

import com.petspa.backend.dto.CategoryDTO;
import com.petspa.backend.dto.PaymentOTP;
import com.petspa.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestBody PaymentOTP request) {
        String paymentUrl = createPaymentUrl(request);
        return ResponseEntity.ok(paymentUrl);
    }

    private String createPaymentUrl(PaymentOTP request) {
        // Tạo URL thanh toán theo yêu cầu SDK VNPAY
        String tmnCode = "vnp_TmnCode";  // Mã do VNPAY cung cấp
        String paymentUrl = "https://pay.vnpay.vn/..."; // URL thanh toán từ VNPAY
        return paymentUrl;
    }
}
