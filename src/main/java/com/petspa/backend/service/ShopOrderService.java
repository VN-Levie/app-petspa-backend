package com.petspa.backend.service;

import com.petspa.backend.dto.ShopOrderDTO;
import com.petspa.backend.dto.ProductQuantityDTO;
import com.petspa.backend.entity.Account;
import com.petspa.backend.entity.Product;
import com.petspa.backend.entity.ShopOrder;
import com.petspa.backend.repository.AccountRepository;
import com.petspa.backend.repository.ProductRepository;
import com.petspa.backend.repository.ShopOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopOrderService {

    @Autowired
    private ShopOrderRepository shopOrderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AccountRepository accountRepository;

    // Lấy tất cả đơn hàng
    public List<ShopOrderDTO> getAll() {
        return shopOrderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Thêm đơn hàng mới
    public ShopOrderDTO add(ShopOrderDTO shopOrderDTO) {
        ShopOrder shopOrder = convertToEntity(shopOrderDTO);
        shopOrder = shopOrderRepository.save(shopOrder);
        return convertToDTO(shopOrder);
    }

    // Cập nhật đơn hàng
    public ShopOrderDTO update(Long id, ShopOrderDTO shopOrderDTO) {
        Optional<ShopOrder> existingOrder = shopOrderRepository.findById(id);
        if (existingOrder.isPresent()) {
            ShopOrder shopOrder = existingOrder.get();
            shopOrder.setTotalPrice(shopOrderDTO.getTotalPrice());
            shopOrder.setStatus(shopOrderDTO.getStatus());
            shopOrder.setPaymentType(shopOrderDTO.getPaymentType());
            shopOrder.setPaymentStatus(shopOrderDTO.getPaymentStatus());
            shopOrder.setDeliveryAddress(shopOrderDTO.getDeliveryAddress());
            shopOrder.setDeliveryDate(shopOrderDTO.getDeliveryDate());
            shopOrder = shopOrderRepository.save(shopOrder);

            return convertToDTO(shopOrder);
        }
        return null;
    }

    // Đếm số lượng đơn hàng theo Account ID
    public Long countShopOrderByAccountId(Long accountId) {
        return shopOrderRepository.countShopOrderByAccountId(accountId);
    }

    // Lấy danh sách đơn hàng theo Account ID
    public List<ShopOrderDTO> getShopOrderByAccount(Long accountId) {
        return shopOrderRepository.getShopOrderByAccount(accountId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Xóa đơn hàng
    public void delete(Long id) {
        shopOrderRepository.softDelete(id);
    }

    // Lấy đơn hàng theo ID
    public ShopOrderDTO getById(Long id) {
        return shopOrderRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // Chuyển đổi từ ShopOrder sang ShopOrderDTO
    private ShopOrderDTO convertToDTO(ShopOrder shopOrder) {
        ShopOrderDTO shopOrderDTO = new ShopOrderDTO();
        shopOrderDTO.setId(shopOrder.getId());
        shopOrderDTO.setAccountId(shopOrder.getAccount().getId());
        shopOrderDTO.setTotalPrice(shopOrder.getTotalPrice());
        shopOrderDTO.setStatus(shopOrder.getStatus());
        shopOrderDTO.setPaymentType(shopOrder.getPaymentType());
        shopOrderDTO.setPaymentStatus(shopOrder.getPaymentStatus());
        shopOrderDTO.setDeliveryAddress(shopOrder.getDeliveryAddress());
        shopOrderDTO.setDeliveryDate(shopOrder.getDeliveryDate());

        // Chuyển đổi danh sách sản phẩm và số lượng (từ Map sang List<ProductQuantityDTO>)
        shopOrderDTO.setProductQuantities(
                shopOrder.getProductQuantities().entrySet().stream()
                        .map(entry -> {
                            ProductQuantityDTO productQuantityDTO = new ProductQuantityDTO();
                            productQuantityDTO.setProductId(entry.getKey().getId());
                            productQuantityDTO.setQuantity(entry.getValue());
                            return productQuantityDTO;
                        })
                        .collect(Collectors.toList())
        );

        return shopOrderDTO;
    }

    @Transactional(readOnly = true)
    public List<ShopOrder> getPendingOrdersByAccountAndDeliveryAddress(Long accountId, String deliveryAddress) {
        return shopOrderRepository.findByAccountIdAndDeliveryAddressAndStatus(accountId, deliveryAddress, "Pending");
    }
    

    // Chuyển đổi từ ShopOrderDTO sang ShopOrder
    private ShopOrder convertToEntity(ShopOrderDTO shopOrderDTO) {
        ShopOrder shopOrder = new ShopOrder();
        shopOrder.setId(shopOrderDTO.getId());
        shopOrder.setTotalPrice(shopOrderDTO.getTotalPrice());
        shopOrder.setStatus(shopOrderDTO.getStatus());
        shopOrder.setPaymentType(shopOrderDTO.getPaymentType());
        shopOrder.setPaymentStatus(shopOrderDTO.getPaymentStatus());
        shopOrder.setDeliveryAddress(shopOrderDTO.getDeliveryAddress());
        shopOrder.setDeliveryDate(shopOrderDTO.getDeliveryDate());

        Account account = accountRepository.findById(shopOrderDTO.getAccountId()).orElseThrow();
        shopOrder.setAccount(account);

        // Chuyển đổi danh sách sản phẩm và số lượng (từ List<ProductQuantityDTO> sang Map<Product, Integer>)
        shopOrder.setProductQuantities(
                shopOrderDTO.getProductQuantities().stream()
                        .collect(Collectors.toMap(
                                productQuantityDTO -> productRepository.findById(productQuantityDTO.getProductId()).orElseThrow(),
                                ProductQuantityDTO::getQuantity
                        ))
        );

        return shopOrder;
    }
}
