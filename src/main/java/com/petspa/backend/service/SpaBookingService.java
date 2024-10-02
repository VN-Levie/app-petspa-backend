package com.petspa.backend.service;

import com.petspa.backend.dto.CategoryDTO;
import com.petspa.backend.dto.SpaBookingDTO;
import com.petspa.backend.dto.SpaBookingDTO;
import com.petspa.backend.dto.SpaBookingDTO;
import com.petspa.backend.entity.Category;
import com.petspa.backend.entity.SpaBooking;
import com.petspa.backend.entity.SpaCategory;
import com.petspa.backend.entity.SpaProduct;
import com.petspa.backend.entity.SpaBooking;
import com.petspa.backend.entity.SpaBooking;
import com.petspa.backend.repository.AccountRepository;
import com.petspa.backend.repository.CategoryRepository;
import com.petspa.backend.repository.PetRepository;
import com.petspa.backend.repository.SpaBookingRepository;
import com.petspa.backend.repository.SpaCategoryRepository;
import com.petspa.backend.repository.SpaProductRepository;
import com.petspa.backend.repository.SpaBookingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpaBookingService {

    @Autowired
    private SpaBookingRepository spaBookingRepository;

    @Autowired
    private SpaProductRepository spaProductRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PetRepository petRepository;

    // Lấy tất cả sản phẩm
    public List<SpaBookingDTO> getAll() {
        return spaBookingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Thêm sản phẩm mới
    public SpaBookingDTO add(SpaBookingDTO categoryDTO) {
        SpaBooking category = convertToEntity(categoryDTO);
        category = spaBookingRepository.save(category);
        return convertToDTO(category);
    }

    // Cập nhật sản phẩm
    public SpaBookingDTO update(Long id, SpaBookingDTO categoryDTO) {
        Optional<SpaBooking> existingCategory = spaBookingRepository.findById(id);
        if (existingCategory.isPresent()) {
            SpaBooking category = existingCategory.get();
            category.setBookedDate(categoryDTO.getBookedDate());
            category.setUsedDate(categoryDTO.getUsedDate());
            category.setUsedTime(categoryDTO.getUsedTime());
            category.setPrice(categoryDTO.getPrice());
            category.setStatus(categoryDTO.getStatus());
            category.setNote(categoryDTO.getNote());
            category = spaBookingRepository.save(category);

            return convertToDTO(category);
        }
        return null;
    }

    // rate and comment
    public SpaBookingDTO rateAndComment(Long id, SpaBookingDTO categoryDTO) {
        Optional<SpaBooking> existingCategory = spaBookingRepository.findById(id);
        if (existingCategory.isPresent()) {
            SpaBooking category = existingCategory.get();
            category.setRating(categoryDTO.getRating());
            category.setComment(categoryDTO.getComment());
            category = spaBookingRepository.save(category);

            return convertToDTO(category);
        }
        return null;
    }

    //// getBookingByAccountId

    public List<SpaBookingDTO> getBookingByAccountId(Long accountId, boolean deleted) {
        return spaBookingRepository.findByAccountIdAndDeleted(accountId, deleted).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Xóa sản phẩm
    public void delete(Long id) {
        spaBookingRepository.softDelete(id);
    }

    // Lấy sản phẩm theo ID
    public SpaBookingDTO getById(Long id) {
        return spaBookingRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // Chuyển đổi từ Category sang CategoryDTO
    private SpaBookingDTO convertToDTO(SpaBooking category) {
        SpaBookingDTO categoryDTO = new SpaBookingDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setAccountId(category.getAccount().getId());
        categoryDTO.setBookedDate(category.getBookedDate());
        categoryDTO.setUsedDate(category.getUsedDate());
        categoryDTO.setUsedTime(category.getUsedTime());
        categoryDTO.setPrice(category.getPrice());
        categoryDTO.setServiceId(category.getService().getId());
        categoryDTO.setPetId(category.getPet().getId());
        categoryDTO.setNote(category.getNote());
        categoryDTO.setPickUpType(category.getPickUpType());
        categoryDTO.setPickUpAddress(category.getPickUpAddress());
        categoryDTO.setReturnType(category.getReturnType());
        categoryDTO.setReturnAddress(category.getReturnAddress());
        categoryDTO.setPaymentType(category.getPaymentType());
        categoryDTO.setStatus(category.getStatus());
        return categoryDTO;

    }

    // Chuyển đổi từ CategoryDTO sang Category
    private SpaBooking convertToEntity(SpaBookingDTO categoryDTO) {
        SpaBooking category = new SpaBooking();
        category.setId(categoryDTO.getId());
        category.setBookedDate(categoryDTO.getBookedDate());
        category.setUsedDate(categoryDTO.getUsedDate());
        category.setUsedTime(categoryDTO.getUsedTime());
        category.setPrice(categoryDTO.getPrice());
        category.setStatus(categoryDTO.getStatus());
        category.setNote(categoryDTO.getNote());
        category.setPickUpType(categoryDTO.getPickUpType());
        category.setPickUpAddress(categoryDTO.getPickUpAddress());
        category.setReturnType(categoryDTO.getReturnType());
        category.setReturnAddress(categoryDTO.getReturnAddress());
        category.setPaymentType(categoryDTO.getPaymentType());
        category.setAccount(accountRepository.findById(categoryDTO.getAccountId()).get());
        category.setPet(petRepository.findById(categoryDTO.getPetId()).get());
        Optional<SpaProduct> spaCategory = spaProductRepository.findById(categoryDTO.getServiceId());
        spaCategory.ifPresent(category::setService);
        return category;
    }
}
