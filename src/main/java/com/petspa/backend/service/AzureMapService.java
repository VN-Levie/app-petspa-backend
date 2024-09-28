package com.petspa.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;

@Service
public class AzureMapService {

    @Value("${azure.maps.api.key}")
    private String azureMapsApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> getCoordinatesFromAddress(String address) {
        try {
            String baseUrl = "https://atlas.microsoft.com/search/address/json";
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                    .queryParam("subscription-key", azureMapsApiKey)
                    .queryParam("api-version", "1.0")
                    .toUriString() + "&query=" + address;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("API key is invalid or expired.");
            } else if (response.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("API key rate limit exceeded.");
            }
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error occurred.");
        }
    }

    public ResponseEntity<String> searchPlace(String query) {
        try {
           
            String baseUrl = "https://atlas.microsoft.com/search/fuzzy/json";

            String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                    .queryParam("subscription-key", azureMapsApiKey)
                    .queryParam("api-version", "1.0")
                    .toUriString() + "&query=" + query;          

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("API key is invalid or expired.");
            } else if (response.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("API key rate limit exceeded.");
            }
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error occurred.");
        }
    }

    public ResponseEntity<String> getAddressFromCoordinates(double lat, double lon) {
        try {
            String baseUrl = "https://atlas.microsoft.com/search/address/reverse/json";
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                    .queryParam("subscription-key", azureMapsApiKey)
                    .queryParam("api-version", "1.0")
                    .toUriString() + "&query=" + lat + "," + lon;

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("API key is invalid or expired.");
            } else if (response.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("API key rate limit exceeded.");
            }
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error occurred.");
        }
    }
}
