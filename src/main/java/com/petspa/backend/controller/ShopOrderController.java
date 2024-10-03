package com.petspa.backend.controller;

import com.petspa.backend.dto.AddressBookDTO;
import com.petspa.backend.dto.ApiResponse;
import com.petspa.backend.dto.ProductQuantityDTO;
import com.petspa.backend.dto.ShopOrderDTO;
import com.petspa.backend.entity.ShopOrder;
import com.petspa.backend.repository.AccountRepository;
import com.petspa.backend.service.ProductService;
import com.petspa.backend.service.ShopOrderService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop-orders")
public class ShopOrderController {

    @Autowired
    private ShopOrderService shopOrderService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProductService productService;

    // Lấy tất cả đơn hàng
    @GetMapping
    public ResponseEntity<ApiResponse> getAllShopOrders() {
        try {
            List<ShopOrderDTO> shopOrders = shopOrderService.getAll();
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "List of shop orders", shopOrders);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Failed to fetch orders", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Lấy đơn hàng theo accountId
    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse> getShopOrdersByAccountId(@PathVariable Long accountId) {
        try {
            List<ShopOrderDTO> shopOrders = shopOrderService.getShopOrderByAccount(accountId);
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "List of shop orders for account",
                    shopOrders);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Failed to fetch orders for account",
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Lấy đơn hàng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getShopOrderById(@PathVariable Long id) {
        try {
            ShopOrderDTO shopOrder = shopOrderService.getById(id);
            if (shopOrder == null) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Shop order not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Shop order details", shopOrder);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("getShopOrderById error: " + e.getMessage());
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Failed to fetch order", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Cập nhật đơn hàng
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateShopOrder(@PathVariable Long id,
            @Valid @RequestBody ShopOrderDTO shopOrderDTO) {
        try {
            ShopOrderDTO updatedShopOrder = shopOrderService.update(id, shopOrderDTO);
            if (updatedShopOrder == null) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Shop order not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Shop order updated successfully",
                    updatedShopOrder);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Failed to update order", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Xóa đơn hàng
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteShopOrder(@PathVariable Long id) {
        try {
            boolean deleted = shopOrderService.delete(id);
            if (deleted) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Shop order deleted successfully", null);
                return ResponseEntity.ok(response);
            } else {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Shop order not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Failed to delete order", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Đếm đơn hàng theo accountId
    @GetMapping("/account/{accountId}/count")
    public ResponseEntity<ApiResponse> countShopOrdersByAccountId(@PathVariable Long accountId) {
        try {
            Long count = shopOrderService.countShopOrderByAccountId(accountId);
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Count of shop orders for account", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Failed to count orders for account",
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Thêm đơn hàng mới
    @PostMapping
    public ResponseEntity<ApiResponse> addShopOrder(@Valid @RequestBody ShopOrderDTO shopOrderDTO) {
        System.out.println(shopOrderDTO);

        // Kiểm tra xem thông tin có hợp lệ hay không
        if (shopOrderDTO.getTotalPrice() == null || shopOrderDTO.getDeliveryAddress().trim().isEmpty()) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Order fields cannot be empty",
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            // Kiểm tra tài khoản có tồn tại hay không
            if (accountRepository.findById(shopOrderDTO.getAccountId()).isEmpty()) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Account not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // Kiểm tra từng sản phẩm trong đơn hàng
            for (ProductQuantityDTO productQuantity : shopOrderDTO.getProductQuantities()) {
                if (productQuantity.getProductId() == null || productQuantity.getQuantity() == null) {
                    ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST,
                            "Product ID and quantity cannot be null", null);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }

                // Kiểm tra số lượng
                if (productQuantity.getQuantity() <= 0) {
                    ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST,
                            "Quantity must be greater than 0", null);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }

                // Kiểm tra sản phẩm có tồn tại hay không
                if (productService.getById(productQuantity.getProductId()) == null) {
                    ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Product not found", null);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
            }

            // Kiểm tra đơn hàng trùng lặp (cùng tài khoản, địa chỉ giao hàng, sản phẩm và
            // số lượng, trạng thái Pending)
            List<ShopOrder> existingOrders = shopOrderService.getPendingOrdersByAccountAndDeliveryAddress(
                    shopOrderDTO.getAccountId(), shopOrderDTO.getDeliveryAddress());

            for (ShopOrder existingOrder : existingOrders) {
                if (existingOrder.getProductQuantities().equals(shopOrderDTO.getProductQuantities())
                        && existingOrder.getTotalPrice().compareTo(shopOrderDTO.getTotalPrice()) == 0) {
                    ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST,
                            "Duplicate pending order found", null);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
            }

            // Thêm đơn hàng mới
            ShopOrderDTO createdShopOrder = shopOrderService.add(shopOrderDTO);
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_CREATED, "Shop order added successfully",
                    createdShopOrder);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST,
                    "An error occurred while processing the order", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // update status
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse> updateStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            ShopOrderDTO updatedShopOrder = shopOrderService.updateStatus(id, status);
            if (updatedShopOrder == null) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Shop order not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Shop order updated successfully",
                    updatedShopOrder);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Failed to update order", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // update payment status
    @PutMapping("/{id}/payment-status")
    public ResponseEntity<ApiResponse> updatePaymentStatus(@PathVariable Long id, @RequestParam String paymentStatus) {
        try {
            ShopOrderDTO updatedShopOrder = shopOrderService.updatePaymentStatus(id, paymentStatus);
            if (updatedShopOrder == null) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Shop order not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Shop order updated successfully",
                    updatedShopOrder);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Failed to update order", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // update delivery date
    @PutMapping("/{id}/delivery-date")
    public ResponseEntity<ApiResponse> updateDeliveryDate(@PathVariable Long id, @RequestParam String deliveryDate) {
        try {
            ShopOrderDTO updatedShopOrder = shopOrderService.updateDeliveryDate(id, deliveryDate);
            if (updatedShopOrder == null) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Shop order not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Shop order updated successfully",
                    updatedShopOrder);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Failed to update order", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}/delivery-address")
    public ResponseEntity<ApiResponse> updateDeliveryAddress(@PathVariable Long id,
            @RequestBody AddressBookDTO deliveryAddress) {
        try {
            System.out.println(deliveryAddress);
            // check if order is not pending
            ShopOrderDTO shopOrder = shopOrderService.getById(id);
            if (!shopOrder.getStatus().equals("Pending")) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST,
                        "Cannot change delivery address because order is not pending", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Update delivery address
            ShopOrderDTO updatedShopOrder = shopOrderService.updateDeliveryAddress(id, deliveryAddress);
            if (updatedShopOrder == null) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Shop order not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            System.out.println(updatedShopOrder);
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Shop order updated successfully",
                    updatedShopOrder);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Failed to update order", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // cancel order
    @PutMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse> cancelOrder(@PathVariable Long id) {
        try {
            ShopOrderDTO shopOrder = shopOrderService.getById(id);
            if (!shopOrder.getStatus().equals("Pending")) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST,
                        "Cannot cancel order that is not pending",
                        null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            ShopOrderDTO updatedShopOrder = shopOrderService.cancelOrder(id);
            if (updatedShopOrder == null) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Shop order not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Shop order updated successfully",
                    updatedShopOrder);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Failed to update order", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
