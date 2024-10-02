package com.petspa.backend.controller;

import com.petspa.backend.dto.ApiResponse;
import com.petspa.backend.dto.ProductDTO;
import com.petspa.backend.service.ProductService;
import com.petspa.backend.service.ShopService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ShopService shopService;

    // Lấy tất cả sản phẩm
    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "0") int offset) {

        List<ProductDTO> products = productService.getAllProducts(category, search, limit, offset);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Fetched products successfully", products);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> showAllProducts() {

        List<ProductDTO> products = productService.getAllProducts();
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Fetched products successfully", products);
        return ResponseEntity.ok(response);
    }

    // Lấy chi tiết một sản phẩm
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        if (product != null) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Fetched product successfully", product);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Thêm sản phẩm mới
    @PostMapping
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO newProduct = productService.addProduct(productDTO);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Added product successfully", newProduct);
        return ResponseEntity.ok(response);
    }

    // Cập nhật sản phẩm
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        if (updatedProduct != null) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Updated product successfully", updatedProduct);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Xóa sản phẩm
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Deleted product successfully");
        return ResponseEntity.ok(response);
    }

    // API để khởi tạo dữ liệu cho Shop
    @PostMapping("/initialize")
    public ResponseEntity<ApiResponse> initializeShopData() {
        shopService.initializeShopData();
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Initialized shop data successfully");
        return ResponseEntity.ok(response);
    }
}
