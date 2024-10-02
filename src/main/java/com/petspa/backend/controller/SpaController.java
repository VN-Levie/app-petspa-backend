package com.petspa.backend.controller;

import com.petspa.backend.dto.ApiResponse;
import com.petspa.backend.dto.PetDTO;
import com.petspa.backend.dto.PetPhotoDTO;
import com.petspa.backend.dto.PetTypeDTO;
import com.petspa.backend.dto.SpaBookingDTO;
import com.petspa.backend.dto.SpaCategoryDTO;
import com.petspa.backend.dto.SpaProductDTO;
import com.petspa.backend.entity.Account;
import com.petspa.backend.entity.PetPhoto;
import com.petspa.backend.entity.SpaBooking;
import com.petspa.backend.repository.AccountRepository;
import com.petspa.backend.dto.PetPhotoCategoryDTO;
import com.petspa.backend.service.PetService;
import com.petspa.backend.service.PetPhotoService;
import com.petspa.backend.service.PetTypeService;
import com.petspa.backend.service.SpaBookingService;
import com.petspa.backend.service.SpaCaregoryService;
import com.petspa.backend.service.SpaProductService;

import jakarta.validation.Valid;

import com.petspa.backend.service.FileUploadService;
import com.petspa.backend.service.FileValidationService;
import com.petspa.backend.service.PetPhotoCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.Optional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/api/spa")
public class SpaController {

    @Autowired
    private PetService petService;

    @Autowired
    private SpaCaregoryService spaCaregoryService;

    @Autowired
    private SpaProductService spaProductService;

    @Autowired
    private SpaBookingService spaBookingService;

    @Autowired
    private AccountRepository accountRepository;

    // #region Spa Category

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse> getAllCategories() {
        List<SpaCategoryDTO> categories = spaCaregoryService.getAllCategories();
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Fetched categories successfully", categories);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/categories")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody SpaCategoryDTO categoryDTO) {
        SpaCategoryDTO addedCategory = spaCaregoryService.addCategory(categoryDTO);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Added category successfully", addedCategory);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id,
            @RequestBody SpaCategoryDTO categoryDTO) {
        SpaCategoryDTO updatedCategory = spaCaregoryService.updateCategory(id, categoryDTO);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Updated category successfully", updatedCategory);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        spaCaregoryService.deleteCategory(id);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Deleted category successfully", null);
        return ResponseEntity.ok(response);
    }

    // count all
    @GetMapping("/categories/count")
    public ResponseEntity<ApiResponse> countAllCategories() {
        Long count = spaCaregoryService.countAll();
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Counted categories successfully", count);
        return ResponseEntity.ok(response);
    }

    // #endregion

    // #region Spa Product

    @GetMapping("/products")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<SpaProductDTO> products = spaProductService.getAll();
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Fetched products successfully", products);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/products")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody SpaProductDTO categoryDTO) {
        SpaProductDTO addedProduct = spaProductService.add(categoryDTO);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Added product successfully", addedProduct);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id,
            @RequestBody SpaProductDTO categoryDTO) {
        SpaProductDTO updatedProduct = spaProductService.update(id, categoryDTO);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Updated product successfully", updatedProduct);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        spaProductService.delete(id);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Deleted product successfully", null);
        return ResponseEntity.ok(response);
    }

    // count all
    @GetMapping("/products/count")
    public ResponseEntity<ApiResponse> countAllProducts() {
        Long count = spaProductService.countAll();
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Counted products successfully", count);
        return ResponseEntity.ok(response);
    }

    // count all products by category id
    @GetMapping("/products/count/{categoryId}")
    public ResponseEntity<ApiResponse> countProductByCategory(@PathVariable Long categoryId) {
        Long count = spaCaregoryService.countProductByCategory(categoryId);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Counted products by category successfully",
                count);
        return ResponseEntity.ok(response);
    }

    // get all products by category id
    @GetMapping("/products/{categoryId}")
    public ResponseEntity<ApiResponse> getAllProductsByCategory(@PathVariable Long categoryId) {
        List<SpaProductDTO> products = spaProductService.getAllByCategory(categoryId);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Fetched products by category successfully",
                products);
        return ResponseEntity.ok(response);
    }

    // #endregion

    // #region Spa Booking

    @GetMapping("/bookings")
    public ResponseEntity<ApiResponse> getAllBookings() {
        List<SpaBookingDTO> bookings = spaBookingService.getAll();
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Fetched bookings successfully", bookings);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/bookings")
    public ResponseEntity<ApiResponse> addBooking(@RequestBody SpaBookingDTO categoryDTO) {

        System.out.println(categoryDTO.toString());
        
        try {

            // "serviceId": 2,
            // "petId": 25,
            // "accountId": 1,
            // "bookedDate": "2024-10-01T04:21:34.274Z",
            // "usedDate": "2024-10-01T04:21:34.274Z",
            // "usedTime": "string",
            // "price": 0,
            // "status": "string",
            // "pickUpType": "string",
            // "returnType": "string",
            // "paymentType": "string"
            //check null
            if (categoryDTO.getAccountId() == null ) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Account id is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            if(categoryDTO.getServiceId() == null){
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Service id is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if(categoryDTO.getPetId() == null){
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Pet id is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if(categoryDTO.getBookedDate() == null){
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Booked date is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if(categoryDTO.getUsedDate() == null){
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Used date is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if(categoryDTO.getUsedTime() == null){
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Used time is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if(categoryDTO.getPrice() == null){
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Price is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if(categoryDTO.getStatus() == null){
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Status is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if(categoryDTO.getPickUpType() == null){
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Pick up type is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if(categoryDTO.getReturnType() == null){
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Return type is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if(categoryDTO.getPaymentType() == null){
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Payment type is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }


            //check account
            Optional<Account> accountOpt = accountRepository.findById(categoryDTO.getAccountId());
            if (accountOpt.isEmpty()) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Account not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }


            SpaBookingDTO addedBooking = spaBookingService.add(categoryDTO);
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_CREATED, "Added booking successfully", addedBooking);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            //e.printStackTrace();
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Added booking failed. Please check your input");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/bookings/{id}")
    public ResponseEntity<ApiResponse> updateBooking(@PathVariable Long id,
            @RequestBody SpaBookingDTO bookingDTO) {

        // Lấy booking theo id
        SpaBookingDTO booking = spaBookingService.getById(id);

        if (booking == null || booking.isDeleted()) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Booking not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Lấy thông tin người dùng hiện tại từ SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Optional<Account> accountOpt = accountRepository.findByEmail(currentUsername);

        if (accountOpt.isEmpty()) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_UNAUTHORIZED, "Unauthorized", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        Account currentUser = accountOpt.get();

        // Kiểm tra quyền admin
        boolean isAdmin = currentUser.getRoles().contains("ROLE_ADMIN");

        // Kiểm tra nếu không phải admin và accountId không khớp
        if (!isAdmin && !booking.getAccountId().equals(currentUser.getId())) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_FORBIDDEN,
                    "You do not have permission to update this booking", null);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        // Kiểm tra trạng thái của booking
        if (booking.getStatus() != SpaBooking.STATUS_PENDING && booking.getStatus() != SpaBooking.STATUS_CONFIRMED) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST,
                    "Cannot update booking with status " + booking.getStatus(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Tiến hành cập nhật
        SpaBookingDTO updatedBooking = spaBookingService.update(id, bookingDTO);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Updated booking successfully", updatedBooking);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/bookings/rate/{id}")
    public ResponseEntity<ApiResponse> rateAndComment(@RequestHeader String token, @PathVariable Long id,
            @RequestBody SpaBookingDTO categoryDTO) {
        // Lấy thông tin người dùng hiện tại từ SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Optional<Account> accountOpt = accountRepository.findByEmail(currentUsername);

        if (accountOpt.isEmpty()) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_UNAUTHORIZED, "Unauthorized", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        Account currentUser = accountOpt.get();

        // Lấy booking theo id
        SpaBookingDTO booking = spaBookingService.getById(id);

        if (booking == null || booking.isDeleted()) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Booking not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Kiểm tra nếu không phải admin và accountId không khớp
        if (!booking.getAccountId().equals(currentUser.getId())) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_FORBIDDEN,
                    "You do not have permission to rate this booking", null);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        SpaBookingDTO updatedBooking = spaBookingService.rateAndComment(id, categoryDTO);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Rated and commented booking successfully",
                updatedBooking);
        return ResponseEntity.ok(response);
    }

    // get booking by account id
    @GetMapping("/bookings/account/{accountId}")
    public ResponseEntity<ApiResponse> getBookingByAccountId(@PathVariable Long accountId) {
        List<SpaBookingDTO> bookings = spaBookingService.getBookingByAccountId(accountId, false);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Fetched bookings by account id successfully",
                bookings);
        return ResponseEntity.ok(response);
    }

    // #endregion

}
