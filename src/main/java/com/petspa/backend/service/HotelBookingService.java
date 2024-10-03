package com.petspa.backend.service;

import com.petspa.backend.dto.HotelBookingDTO;
import com.petspa.backend.entity.Account;
import com.petspa.backend.entity.HotelBooking;
import com.petspa.backend.entity.Pet;
import com.petspa.backend.repository.AccountRepository;
import com.petspa.backend.repository.HotelBookingRepository;
import com.petspa.backend.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelBookingService {

    @Autowired
    private HotelBookingRepository hotelBookingRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PetRepository petRepository;

    // Lấy danh sách tất cả HotelBooking
    public List<HotelBookingDTO> getAll() {
        return hotelBookingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Thêm một booking mới
    public HotelBookingDTO add(HotelBookingDTO hotelBookingDTO) {
        HotelBooking hotelBooking = convertToEntity(hotelBookingDTO);
        hotelBooking = hotelBookingRepository.save(hotelBooking);
        return convertToDTO(hotelBooking);
    }

    // Cập nhật một booking
    public HotelBookingDTO update(Long id, HotelBookingDTO hotelBookingDTO) {
        Optional<HotelBooking> existingBooking = hotelBookingRepository.findById(id);
        if (existingBooking.isPresent()) {
            HotelBooking hotelBooking = existingBooking.get();
            hotelBooking.setCheckInDate(hotelBookingDTO.getCheckInDate());
            hotelBooking.setCheckOutDate(hotelBookingDTO.getCheckOutDate());
            hotelBooking.setTotalPrice(hotelBookingDTO.getTotalPrice());
            hotelBooking.setStatus(hotelBookingDTO.getStatus());
            hotelBooking.setPickUpType(hotelBookingDTO.getPickUpType());
            hotelBooking.setPickUpAddress(hotelBookingDTO.getPickUpAddress());
            hotelBooking.setReturnType(hotelBookingDTO.getReturnType());
            hotelBooking.setReturnAddress(hotelBookingDTO.getReturnAddress());
            hotelBooking.setPaymentType(hotelBookingDTO.getPaymentType());
            hotelBooking.setNote(hotelBookingDTO.getNote());
            hotelBooking.setRating(hotelBookingDTO.getRating());
            hotelBooking.setComment(hotelBookingDTO.getComment());

            hotelBooking = hotelBookingRepository.save(hotelBooking);
            return convertToDTO(hotelBooking);
        }
        return null;
    }

    //getById
    public HotelBookingDTO getById(Long id) {
        return hotelBookingRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // Xóa một booking
    public boolean delete(Long id) {
        Optional<HotelBooking> booking = hotelBookingRepository.findById(id);
        if (booking.isPresent()) {
            hotelBookingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Lấy danh sách bookings theo account
    public List<HotelBookingDTO> getByAccountId(Long accountId) {
        return hotelBookingRepository.findByAccountId(accountId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Cập nhật trạng thái của booking
    @Transactional
    public boolean updateStatus(Long id, String status) {
        return hotelBookingRepository.updateStatus(id, status) > 0;
    }

    // Cập nhật rating và comment
    @Transactional
    public boolean updateRatingAndComment(Long id, Integer rating, String comment) {
        return hotelBookingRepository.updateRatingAndComment(id, rating, comment) > 0;
    }

    // Chuyển đổi từ entity sang DTO
    private HotelBookingDTO convertToDTO(HotelBooking hotelBooking) {
        HotelBookingDTO hotelBookingDTO = new HotelBookingDTO();
        hotelBookingDTO.setId(hotelBooking.getId());
        hotelBookingDTO.setPetId(hotelBooking.getPet().getId());
        hotelBookingDTO.setAccountId(hotelBooking.getAccount().getId());
        hotelBookingDTO.setCheckInDate(hotelBooking.getCheckInDate());
        hotelBookingDTO.setCheckOutDate(hotelBooking.getCheckOutDate());
        hotelBookingDTO.setTotalPrice(hotelBooking.getTotalPrice());
        hotelBookingDTO.setStatus(hotelBooking.getStatus());
        hotelBookingDTO.setPickUpType(hotelBooking.getPickUpType());
        hotelBookingDTO.setPickUpAddress(hotelBooking.getPickUpAddress());
        hotelBookingDTO.setReturnType(hotelBooking.getReturnType());
        hotelBookingDTO.setReturnAddress(hotelBooking.getReturnAddress());
        hotelBookingDTO.setPaymentType(hotelBooking.getPaymentType());
        hotelBookingDTO.setNote(hotelBooking.getNote());
        hotelBookingDTO.setRating(hotelBooking.getRating());
        hotelBookingDTO.setComment(hotelBooking.getComment());
        return hotelBookingDTO;
    }

    // Chuyển đổi từ DTO sang entity
    private HotelBooking convertToEntity(HotelBookingDTO hotelBookingDTO) {
        HotelBooking hotelBooking = new HotelBooking();
        hotelBooking.setId(hotelBookingDTO.getId());
        Account account = accountRepository.findById(hotelBookingDTO.getAccountId()).orElseThrow();
        Pet pet = petRepository.findById(hotelBookingDTO.getPetId()).orElseThrow();
        hotelBooking.setAccount(account);
        hotelBooking.setPet(pet);
        hotelBooking.setCheckInDate(hotelBookingDTO.getCheckInDate());
        hotelBooking.setCheckOutDate(hotelBookingDTO.getCheckOutDate());
        hotelBooking.setTotalPrice(hotelBookingDTO.getTotalPrice());
        hotelBooking.setStatus(hotelBookingDTO.getStatus());
        hotelBooking.setPickUpType(hotelBookingDTO.getPickUpType());
        hotelBooking.setPickUpAddress(hotelBookingDTO.getPickUpAddress());
        hotelBooking.setReturnType(hotelBookingDTO.getReturnType());
        hotelBooking.setReturnAddress(hotelBookingDTO.getReturnAddress());
        hotelBooking.setPaymentType(hotelBookingDTO.getPaymentType());
        hotelBooking.setNote(hotelBookingDTO.getNote());
        hotelBooking.setRating(hotelBookingDTO.getRating());
        hotelBooking.setComment(hotelBookingDTO.getComment());
        return hotelBooking;
    }
}
