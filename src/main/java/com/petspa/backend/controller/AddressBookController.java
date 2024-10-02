package com.petspa.backend.controller;

import com.petspa.backend.dto.AddressBookDTO;
import com.petspa.backend.dto.ApiResponse;
import com.petspa.backend.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address-books")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    // Lấy tất cả địa chỉ
    @GetMapping
    public ResponseEntity<ApiResponse> getAllAddressBooks() {
        List<AddressBookDTO> addressBooks = addressBookService.getAll();
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "List of address books", addressBooks);
        return ResponseEntity.ok(response);
    }

    // Lấy địa chỉ theo accountId
    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse> getAddressBooksByAccountId(@PathVariable Long accountId) {
        List<AddressBookDTO> addressBooks = addressBookService.getByAccountId(accountId);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "List of address books for account", addressBooks);
        return ResponseEntity.ok(response);
    }

    //count address by accountId
    @GetMapping("/account/{accountId}/count")
    public ResponseEntity<ApiResponse> countAddressBooksByAccountId(@PathVariable Long accountId) {
        Long count = addressBookService.countByAccountId(accountId);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Count of address books for account", count);
        return ResponseEntity.ok(response);
    }

    // Lấy địa chỉ theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getAddressBookById(@PathVariable Long id) {
        AddressBookDTO addressBook = addressBookService.getById(id);
        if (addressBook == null) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Address not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Address details", addressBook);
        return ResponseEntity.ok(response);
    }



    // Thêm địa chỉ mới
    @PostMapping
    public ResponseEntity<ApiResponse> addAddressBook(@RequestBody AddressBookDTO addressBookDTO) {
        //trim and check empty
        if (addressBookDTO.getStreet().trim().isEmpty() || addressBookDTO.getCity().trim().isEmpty() || addressBookDTO.getState().trim().isEmpty() || addressBookDTO.getPostalCode().trim().isEmpty() || addressBookDTO.getCountry().trim().isEmpty() || addressBookDTO.getFullName().trim().isEmpty()) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_BAD_REQUEST, "Address fields cannot be empty", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        AddressBookDTO createdAddressBook = addressBookService.add(addressBookDTO);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_CREATED, "Address added successfully", createdAddressBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Cập nhật địa chỉ
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateAddressBook(@PathVariable Long id, @RequestBody AddressBookDTO addressBookDTO) {
        AddressBookDTO updatedAddressBook = addressBookService.update(id, addressBookDTO);
        if (updatedAddressBook == null) {
            ApiResponse response = new ApiResponse(ApiResponse.STATUS_NOT_FOUND, "Address not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Address updated successfully", updatedAddressBook);
        return ResponseEntity.ok(response);
    }

    // Xóa địa chỉ
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAddressBook(@PathVariable Long id) {
        addressBookService.delete(id);
        ApiResponse response = new ApiResponse(ApiResponse.STATUS_OK, "Address deleted successfully", null);
        return ResponseEntity.ok(response);
    }
}
