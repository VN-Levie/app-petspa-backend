package com.petspa.backend.service;

import com.petspa.backend.dto.AddressBookDTO;
import com.petspa.backend.entity.Account;
import com.petspa.backend.entity.AddressBook;
import com.petspa.backend.repository.AccountRepository;
import com.petspa.backend.repository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressBookService {

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Autowired
    private AccountRepository accountRepository;

    // Lấy tất cả các địa chỉ
    public List<AddressBookDTO> getAll() {
        return addressBookRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy danh sách địa chỉ theo accountId
    public List<AddressBookDTO> getByAccountId(Long accountId) {
        return addressBookRepository.findByAccountId(accountId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // count address by accountId
    public Long countByAccountId(Long accountId) {
        return addressBookRepository.countByAccountId(accountId);
    }

    // Thêm địa chỉ mới
    public AddressBookDTO add(AddressBookDTO addressBookDTO) {
        AddressBook addressBook = convertToEntity(addressBookDTO);
        addressBook = addressBookRepository.save(addressBook);
        return convertToDTO(addressBook);
    }

    // Cập nhật địa chỉ
    public AddressBookDTO update(Long id, AddressBookDTO addressBookDTO) {
        Optional<AddressBook> existingAddressBook = addressBookRepository.findById(id);
        if (existingAddressBook.isPresent()) {
            AddressBook addressBook = existingAddressBook.get();
            addressBook.setStreet(addressBookDTO.getStreet());
            addressBook.setCity(addressBookDTO.getCity());
            addressBook.setState(addressBookDTO.getState());
            addressBook.setPostalCode(addressBookDTO.getPostalCode());
            addressBook.setCountry(addressBookDTO.getCountry());
            addressBook.setFullName(addressBookDTO.getFullName());
            addressBook.setPhoneNumber(addressBookDTO.getPhoneNumber());
            addressBook.setEmail(addressBookDTO.getEmail());

            addressBook = addressBookRepository.save(addressBook);
            return convertToDTO(addressBook);
        }
        return null;
    }

    // Xóa địa chỉ
    public void delete(Long id) {
        addressBookRepository.deleteById(id);
    }

    // Lấy địa chỉ theo ID
    public AddressBookDTO getById(Long id) {
        return addressBookRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // Chuyển đổi từ AddressBook sang AddressBookDTO
    private AddressBookDTO convertToDTO(AddressBook addressBook) {
        AddressBookDTO addressBookDTO = new AddressBookDTO();
        addressBookDTO.setId(addressBook.getId());
        addressBookDTO.setStreet(addressBook.getStreet());
        addressBookDTO.setCity(addressBook.getCity());
        addressBookDTO.setState(addressBook.getState());
        addressBookDTO.setPostalCode(addressBook.getPostalCode());
        addressBookDTO.setCountry(addressBook.getCountry());
        addressBookDTO.setFullName(addressBook.getFullName());
        addressBookDTO.setPhoneNumber(addressBook.getPhoneNumber());
        addressBookDTO.setEmail(addressBook.getEmail());
        addressBookDTO.setAccountId(addressBook.getAccount().getId());
        return addressBookDTO;
    }

    // Chuyển đổi từ AddressBookDTO sang AddressBook
    private AddressBook convertToEntity(AddressBookDTO addressBookDTO) {
        AddressBook addressBook = new AddressBook();
        addressBook.setId(addressBookDTO.getId());
        addressBook.setStreet(addressBookDTO.getStreet());
        addressBook.setCity(addressBookDTO.getCity());
        addressBook.setState(addressBookDTO.getState());
        addressBook.setPostalCode(addressBookDTO.getPostalCode());
        addressBook.setCountry(addressBookDTO.getCountry());
        addressBook.setFullName(addressBookDTO.getFullName());
        addressBook.setPhoneNumber(addressBookDTO.getPhoneNumber());
        addressBook.setEmail(addressBookDTO.getEmail());

        Account account = accountRepository.findById(addressBookDTO.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));
        addressBook.setAccount(account);
        return addressBook;
    }
}
