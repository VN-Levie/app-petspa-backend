package com.petspa.backend.controller;

import com.petspa.backend.service.AzureMapService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AzureMapController {

    @Autowired
    private AzureMapService azureMapService;

    @GetMapping("/get-coordinates")
    @Operation(summary = "Get latitude and longitude from address", description = "Returns latitude and longitude based on the input address using Azure Maps API.")
    public ResponseEntity<String> getCoordinatesFromAddress(@RequestParam String address) {
        return azureMapService.getCoordinatesFromAddress(address);
    }

    @GetMapping("/search-place")
    @Operation(summary = "Search for a place", description = "Returns search results for a place based on the input query using Azure Maps API.")
    public ResponseEntity<String> searchPlace(@RequestParam String query) {
        return azureMapService.searchPlace(query);
    }

    @GetMapping("/get-address")
    @Operation(summary = "Get address from latitude and longitude", description = "Returns address based on the input latitude and longitude using Azure Maps API.")
    public ResponseEntity<String> getAddressFromCoordinates(@RequestParam double lat, @RequestParam double lon) {
        return azureMapService.getAddressFromCoordinates(lat, lon);
    }
}
