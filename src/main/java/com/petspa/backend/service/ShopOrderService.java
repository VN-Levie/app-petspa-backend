package com.petspa.backend.service;

import com.petspa.backend.dto.ShopOrderDTO;
import com.petspa.backend.dto.AddressBookDTO;
import com.petspa.backend.dto.ProductQuantityDTO;
import com.petspa.backend.entity.Account;
import com.petspa.backend.entity.Product;
import com.petspa.backend.entity.ShopOrder;
import com.petspa.backend.repository.AccountRepository;
import com.petspa.backend.repository.ProductRepository;
import com.petspa.backend.repository.ShopOrderRepository;

import org.checkerframework.checker.units.qual.s;
import org.hibernate.Hibernate;
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
    @Transactional
    public List<ShopOrderDTO> getShopOrderByAccount(Long accountId) {
        List<ShopOrder> shopOrders = shopOrderRepository.getShopOrderByAccount(accountId);
        for (ShopOrder order : shopOrders) {
            Hibernate.initialize(order.getProductQuantities());
        }
        return shopOrders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Xóa đơn hàng
    public boolean delete(Long id) {
        return shopOrderRepository.softDelete(id);
    }

    // Lấy đơn hàng theo ID
    @Transactional(readOnly = true)
    public ShopOrderDTO getById(Long id) {
        Optional<ShopOrder> shopOrderOptional = shopOrderRepository.findById(id);
        if (shopOrderOptional.isPresent()) {
            ShopOrder shopOrder = shopOrderOptional.get();
            Hibernate.initialize(shopOrder.getProductQuantities());
            return convertToDTO(shopOrder);
        }
        return null;
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
        shopOrderDTO.setReceiverName(shopOrder.getReceiverName());
        shopOrderDTO.setReceiverPhone(shopOrder.getReceiverPhone());
        shopOrderDTO.setReceiverEmail(shopOrder.getReceiverEmail());
        shopOrderDTO.setReceiverAddressId(shopOrder.getReceiverAddressId());

        // Chuyển đổi danh sách sản phẩm và số lượng (từ Map sang
        // List<ProductQuantityDTO>)
        shopOrderDTO.setProductQuantities(
                shopOrder.getProductQuantities().entrySet().stream()
                        .map(entry -> {
                            ProductQuantityDTO productQuantityDTO = new ProductQuantityDTO();
                            productQuantityDTO.setProductId(entry.getKey().getId());
                            productQuantityDTO.setQuantity(entry.getValue());
                            return productQuantityDTO;
                        })
                        .collect(Collectors.toList()));

        return shopOrderDTO;
    }

    @Transactional(readOnly = true)
    public List<ShopOrder> getPendingOrdersByAccountAndDeliveryAddress(Long accountId, String deliveryAddress) {
        List<ShopOrder> orders = shopOrderRepository.findByAccountIdAndDeliveryAddressAndStatus(accountId,
                deliveryAddress, "Pending");

        // Khởi tạo productQuantities để tránh lỗi LazyInitializationException
        orders.forEach(order -> Hibernate.initialize(order.getProductQuantities()));

        return orders;
    }

    // shopOrderService.updateStatus(id, status);
    @Transactional
    public ShopOrderDTO updateStatus(Long id, String status) {
        shopOrderRepository.updateStatus(id, status);
        return getById(id);
    }

    // shopOrderService.updatePaymentStatus(id, paymentStatus);
    @Transactional
    public ShopOrderDTO updatePaymentStatus(Long id, String paymentStatus) {
        shopOrderRepository.updatePaymentStatus(id, paymentStatus);
        return getById(id);
    }

    // shopOrderService.updateDeliveryDate(id, deliveryDate);
    @Transactional
    public ShopOrderDTO updateDeliveryDate(Long id, String deliveryDate) {
        shopOrderRepository.updateDeliveryDate(id, deliveryDate);
        return getById(id);
    }

    //updateDeliveryAddress(id, deliveryAddress);
    @Transactional
    public ShopOrderDTO updateDeliveryAddress(Long id, AddressBookDTO deliveryAddress) {
        //lấy ra đơn hàng
        ShopOrder shopOrder = shopOrderRepository.findById(id).orElseThrow();
        //cập nhật địa chỉ giao hàng
        // shopOrder.setDeliveryAddress(deliveryAddress.getStreet() + ", " + deliveryAddress.getCity() + ", "
        //         + deliveryAddress.getState() + ", " + deliveryAddress.getPostalCode() + ", " + deliveryAddress.getCountry());
        // String fullAddress = '${address.street}, ${address.city}, ${address.state}, ${address.country}, ${address.postalCode}';
        shopOrder.setDeliveryAddress(deliveryAddress.getStreet() + ", " + deliveryAddress.getCity() + ", "
                + deliveryAddress.getState() + ", " + deliveryAddress.getCountry() + ", " + deliveryAddress.getPostalCode());
        //cập nhật tên người nhận hàng
        shopOrder.setReceiverName(deliveryAddress.getFullName());
        //cập nhật số điện thoại người nhận hàng
        shopOrder.setReceiverPhone(deliveryAddress.getPhoneNumber());
        //cập nhật email người nhận hàng
        shopOrder.setReceiverEmail(deliveryAddress.getEmail());
        //cập nhật id địa chỉ người nhận hàng
        shopOrder.setReceiverAddressId(deliveryAddress.getId());
        //lưu đơn hàng
        shopOrder = shopOrderRepository.save(shopOrder);
        return convertToDTO(shopOrder);

    }

    //cancelOrder(id);
    @Transactional
    public ShopOrderDTO cancelOrder(Long id) {

        shopOrderRepository.cancelOrder(id);
        return getById(id);
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
        shopOrder.setReceiverName(shopOrderDTO.getReceiverName());
        shopOrder.setReceiverPhone(shopOrderDTO.getReceiverPhone());
        shopOrder.setReceiverEmail(shopOrderDTO.getReceiverEmail());
        shopOrder.setReceiverAddressId(shopOrderDTO.getReceiverAddressId());

        Account account = accountRepository.findById(shopOrderDTO.getAccountId()).orElseThrow();
        shopOrder.setAccount(account);

        // Chuyển đổi danh sách sản phẩm và số lượng (từ List<ProductQuantityDTO> sang
        // Map<Product, Integer>)
        shopOrder.setProductQuantities(
                shopOrderDTO.getProductQuantities().stream()
                        .collect(Collectors.toMap(
                                productQuantityDTO -> productRepository.findById(productQuantityDTO.getProductId())
                                        .orElseThrow(),
                                ProductQuantityDTO::getQuantity)));

        return shopOrder;
    }
}
