package com.petspa.backend.controller;

import com.petspa.backend.dto.ApiResponse;
import com.petspa.backend.dto.HotelBookingDTO;
import com.petspa.backend.entity.HotelBooking;
import com.petspa.backend.service.HotelBookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel-bookings")
public class HotelBookingController {

    @Autowired
    private HotelBookingService hotelBookingService;

    // Lấy tất cả hotel bookings
    @GetMapping
    public ResponseEntity<ApiResponse> getAllHotelBookings() {
        try {
            List<HotelBookingDTO> hotelBookings = hotelBookingService.getAll();
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "List of hotel bookings", hotelBookings);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Failed to fetch bookings", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Lấy hotel booking theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getHotelBookingById(@PathVariable Long id) {
        try {
            HotelBookingDTO hotelBooking = hotelBookingService.getById(id);
            if (hotelBooking == null) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Hotel booking not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Hotel booking details", hotelBooking);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Failed to fetch booking", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Lấy hotel booking theo account ID
    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse> getHotelBookingsByAccountId(@PathVariable Long accountId) {
        try {
            List<HotelBookingDTO> hotelBookings = hotelBookingService.getByAccountId(accountId);
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "List of hotel bookings for account",
                    hotelBookings);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST,
                    "Failed to fetch bookings for account", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Thêm mới hotel booking
    @PostMapping
    public ResponseEntity<ApiResponse> addHotelBooking(@Valid @RequestBody HotelBookingDTO hotelBookingDTO) {
        try {
            HotelBookingDTO createdHotelBooking = hotelBookingService.add(hotelBookingDTO);
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_CREATED, "Hotel booking added successfully",
                    createdHotelBooking);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Failed to add booking", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Cập nhật hotel booking
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateHotelBooking(@PathVariable Long id,
            @Valid @RequestBody HotelBookingDTO hotelBookingDTO) {
        try {
            HotelBookingDTO updatedHotelBooking = hotelBookingService.update(id, hotelBookingDTO);
            if (updatedHotelBooking == null) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Hotel booking not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Hotel booking updated successfully",
                    updatedHotelBooking);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Failed to update booking", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Xóa hotel booking
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteHotelBooking(@PathVariable Long id) {
        try {
            boolean deleted = hotelBookingService.delete(id);
            if (deleted) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Hotel booking deleted successfully",
                        null);
                return ResponseEntity.ok(response);
            } else {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Hotel booking not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Failed to delete booking", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Cập nhật trạng thái booking
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse> updateStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            boolean updated = hotelBookingService.updateStatus(id, status);
            if (!updated) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Hotel booking not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Hotel booking status updated successfully",
                    null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Failed to update status", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Cập nhật rating và comment
    @PutMapping("/{id}/rating")
    public ResponseEntity<ApiResponse> updateRatingAndComment(@PathVariable Long id, @RequestParam Integer rating,
            @RequestParam String comment) {
        try {
            boolean updated = hotelBookingService.updateRatingAndComment(id, rating, comment);
            if (!updated) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Hotel booking not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK,
                    "Hotel booking rating and comment updated successfully", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST,
                    "Failed to update rating and comment", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // api/hotel-bookings/cancel/1
    @PutMapping("/cancel/{id}")
    public ResponseEntity<ApiResponse> cancelHotelBooking(@PathVariable Long id) {
        try {
            // check if booking exists
            HotelBookingDTO hotelBooking = hotelBookingService.getById(id);
            if (hotelBooking == null) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Hotel booking not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            // check current status
            if (!hotelBooking.getStatus().equals(HotelBooking.STATUS_PENDING)) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST,
                        "Cannot cancel booking that is not pending", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            boolean updated = hotelBookingService.updateStatus(id, HotelBooking.STATUS_CANCELLED);
            if (!updated) {
                ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Hotel booking not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Hotel booking cancelled successfully", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Failed to cancel booking", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
