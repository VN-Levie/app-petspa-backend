package com.petspa.backend.controller;

import com.petspa.backend.dto.ApiResponse;
import com.petspa.backend.service.AzureMapService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/map")
public class AzureMapController {

    @Autowired
    private AzureMapService azureMapService;

    private static final double SHOP_LATITUDE = 10.74367076214558;
    private static final double SHOP_LONGITUDE = 106.64320065300335;

    @GetMapping("/get-coordinates")
    @Operation(summary = "Get latitude and longitude from address", description = "Returns latitude and longitude based on the input address using Azure Maps API.")
    public ResponseEntity<ApiResponse> getCoordinatesFromAddress(@RequestParam String address) {
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Fetched coordinates successfully",
                azureMapService.getCoordinatesFromAddress(address).getBody());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search-place")
    @Operation(summary = "Search for a place", description = "Returns search results for a place based on the input query using Azure Maps API.")
    public ResponseEntity<ApiResponse> searchPlace(@RequestParam String query) {
        // return azureMapService.searchPlace(query);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Fetched search results successfully",
                azureMapService.searchPlace(query).getBody());
        return ResponseEntity.ok(response);

    }

    @GetMapping("/get-address")
    @Operation(summary = "Get address from latitude and longitude", description = "Returns address based on the input latitude and longitude using Azure Maps API.")
    public ResponseEntity<ApiResponse> getAddressFromCoordinates(@RequestParam double lat, @RequestParam double lon) {
        // return azureMapService.getAddressFromCoordinates(lat, lon);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Fetched address successfully",
                azureMapService.getAddressFromCoordinates(lat, lon).getBody());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-distance-and-time")
    @Operation(summary = "Calculate distance and time from shop to user's location", description = "Calculates the distance and travel time from the shop to the user's location using Azure Maps API.")
    public ResponseEntity<ApiResponse> getDistanceAndTime(@RequestParam double userLat, @RequestParam double userLon) {
        ResponseEntity<String> routeResponse = azureMapService.getRouteFromShopToUser(SHOP_LATITUDE, SHOP_LONGITUDE,
                userLat, userLon);

        // Bạn có thể xử lý thêm tùy vào kết quả trả về từ service
        if (routeResponse.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity
                    .ok(new ApiResponse(ApiResponse.STATUS_OK, "Route fetched successfully", routeResponse.getBody()));
        } else {
            return ResponseEntity.status(routeResponse.getStatusCode())
                    .body(new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Failed to fetch route", null));
        }
    }
}
