package com.petspa.backend.controller;

import com.petspa.backend.dto.ApiResponse;
import com.petspa.backend.dto.PetTagOrderDTO;
import com.petspa.backend.service.PetTagOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pet-tag-orders")
public class PetTagOrderController {

    @Autowired
    private PetTagOrderService petTagOrderService;

    // Lấy tất cả đơn hàng
    @GetMapping
    public ResponseEntity<ApiResponse> getAllOrders() {
        List<PetTagOrderDTO> orders = petTagOrderService.getAll();
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "List of pet tag orders", orders);
        return ResponseEntity.ok(response);
    }

    // Lấy đơn hàng theo Account ID
    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse> getOrdersByAccountId(@PathVariable Long accountId) {
        List<PetTagOrderDTO> orders = petTagOrderService.getOrdersByAccountId(accountId);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "List of pet tag orders for account", orders);
        return ResponseEntity.ok(response);
    }

    // Thêm đơn hàng mới
    @PostMapping
    public ResponseEntity<ApiResponse> addOrder(@RequestBody PetTagOrderDTO orderDTO) {
       
        PetTagOrderDTO createdOrder = petTagOrderService.addOrder(orderDTO);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_CREATED, "Pet tag order created successfully", createdOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Xóa đơn hàng
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable Long id) {
        petTagOrderService.deleteOrder(id);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Pet tag order deleted successfully", null);
        return ResponseEntity.ok(response);
    }
}
