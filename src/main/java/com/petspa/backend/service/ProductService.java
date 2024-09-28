package com.petspa.backend.service;

import com.petspa.backend.dto.ProductDTO;
import com.petspa.backend.entity.Category;
import com.petspa.backend.entity.Product;
import com.petspa.backend.repository.CategoryRepository;
import com.petspa.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Lấy tất cả sản phẩm
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

     // Lấy tất cả sản phẩm theo phân trang và tìm kiếm
     public List<ProductDTO> getAllProducts(String category, String search, int limit, int offset) {
        Pageable pageable = PageRequest.of(offset / limit, limit); // Tạo Pageable từ offset và limit
        Page<Product> productPage = productRepository.findAllProducts(category, search, pageable);
        
        return productPage.stream()
                          .map(this::mapToDTO)
                          .collect(Collectors.toList());
    }

    // Map Product entity sang ProductDTO
    private ProductDTO mapToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                product.getCategory().getName()
        );
    }

     // Lấy một sản phẩm theo ID
     public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    // Map ProductDTO sang Product entity
    private Product mapToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setImageUrl(productDTO.getImageUrl());
        // Đặt category bằng cách tìm theo tên hoặc ID trong repository
        // product.setCategory(categoryRepository.findByName(productDTO.getCategory()));
        return product;
    }


    // Thêm sản phẩm mới
    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        product = productRepository.save(product);
        return convertToDTO(product);
    }

    // Cập nhật sản phẩm
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setImageUrl(productDTO.getImageUrl());

            Optional<Category> category = categoryRepository.findByName(productDTO.getCategory());
            category.ifPresent(product::setCategory);

            product = productRepository.save(product);
            return convertToDTO(product);
        }
        return null;
    }

    // Xóa sản phẩm
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Chuyển đổi từ Product sang ProductDTO
    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setImageUrl(product.getImageUrl());
        productDTO.setCategory(product.getCategory().getName());
        return productDTO;
    }

    // Chuyển đổi từ ProductDTO sang Product
    private Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setImageUrl(productDTO.getImageUrl());

        Optional<Category> category = categoryRepository.findByName(productDTO.getCategory());
        category.ifPresent(product::setCategory);

        return product;
    }
}
