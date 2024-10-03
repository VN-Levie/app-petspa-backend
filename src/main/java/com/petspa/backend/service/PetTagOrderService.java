package com.petspa.backend.service;

import com.petspa.backend.dto.PetTagOrderDTO;
import com.petspa.backend.entity.Account;
import com.petspa.backend.entity.PetTag;
import com.petspa.backend.entity.PetTagOrder;
import com.petspa.backend.repository.AccountRepository;
import com.petspa.backend.repository.PetTagOrderRepository;
import com.petspa.backend.repository.PetTagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetTagOrderService {

    @Autowired
    private PetTagOrderRepository petTagOrderRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PetTagRepository petTagRepository;

    // Lấy tất cả đơn hàng bảng tên thú cưng
    public List<PetTagOrderDTO> getAll() {
        return petTagOrderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy đơn hàng theo Account ID
    public List<PetTagOrderDTO> getOrdersByAccountId(Long accountId) {
        return petTagOrderRepository.findByAccountId(accountId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Thêm đơn hàng mới
    public PetTagOrderDTO addOrder(PetTagOrderDTO orderDTO) {
        PetTagOrder order = convertToEntity(orderDTO);
        order = petTagOrderRepository.save(order);
        return convertToDTO(order);
    }

    // Lấy đơn hàng theo ID
    public PetTagOrderDTO getOrderById(Long id) {
        Optional<PetTagOrder> order = petTagOrderRepository.findById(id);
        return order.map(this::convertToDTO).orElse(null);
    }

    // Xóa đơn hàng
    public void deleteOrder(Long id) {
        petTagOrderRepository.deleteById(id);
    }

    // Chuyển đổi từ Entity sang DTO
    private PetTagOrderDTO convertToDTO(PetTagOrder order) {
        PetTagOrderDTO dto = new PetTagOrderDTO();
        dto.setId(order.getId());
        dto.setAccountId(order.getAccount().getId());
        dto.setPetTagId(order.getPetTag().getId());
        dto.setTextFront(order.getTextFront());
        dto.setTextBack(order.getTextBack());
        dto.setNum(order.getNum());
        dto.setFullAddress(order.getFullAddress());
        dto.setReceiverName(order.getReceiverName());
        dto.setReceiverPhone(order.getReceiverPhone());
        dto.setReceiverEmail(order.getReceiverEmail());
        dto.setReceiverAddressId(order.getReceiverAddressId());
        return dto;
    }

    // Chuyển đổi từ DTO sang Entity
    private PetTagOrder convertToEntity(PetTagOrderDTO dto) {
        PetTagOrder order = new PetTagOrder();

        // Gán giá trị từ DTO
        order.setId(dto.getId());

        // Lấy đối tượng Account từ accountId
        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        order.setAccount(account); // Gán đối tượng Account

        // Lấy đối tượng PetTag từ petTagId
        PetTag petTag = petTagRepository.findById(dto.getPetTagId())
                .orElseThrow(() -> new IllegalArgumentException("Pet Tag not found"));
        order.setPetTag(petTag); // Gán đối tượng PetTag

        // Gán các trường còn lại
        order.setTextFront(dto.getTextFront());
        order.setTextBack(dto.getTextBack());
        order.setNum(dto.getNum());
        order.setFullAddress(dto.getFullAddress());
        order.setReceiverName(dto.getReceiverName());
        order.setReceiverPhone(dto.getReceiverPhone());
        order.setReceiverEmail(dto.getReceiverEmail());
        order.setReceiverAddressId(dto.getReceiverAddressId());

        return order;
    }
}
