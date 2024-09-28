package com.petspa.backend.service;

import com.petspa.backend.entity.Category;
import com.petspa.backend.entity.Product;
import com.petspa.backend.repository.CategoryRepository;
import com.petspa.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ShopService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    // Dữ liệu mẫu cho sản phẩm
    private final List<String> foodNames = Arrays.asList("Thức ăn cho chó", "Thức ăn cho mèo", "Snack cho chó", "Snack cho mèo", "Hạt dinh dưỡng", "Thức ăn khô");
    private final List<String> toyNames = Arrays.asList("Bóng đồ chơi", "Xương gặm cao su", "Đồ chơi nhai", "Đồ chơi phát âm", "Dây thừng kéo co", "Chuột đồ chơi");

    // Khởi tạo dữ liệu cho Shop (Sản phẩm và danh mục)
    public void initializeShopData() {
        if (productRepository.findAll().isEmpty()) {
            List<Map<String, List<String>>> categories = new ArrayList<>();

            categories.add(Map.of("Thức ăn", foodNames));
            categories.add(Map.of("Đồ chơi", toyNames));

            for (Map<String, List<String>> categoryMap : categories) {
                for (Map.Entry<String, List<String>> entry : categoryMap.entrySet()) {
                    String categoryName = entry.getKey();
                    List<String> productNames = entry.getValue();

                    Category category = categoryRepository.findByName(categoryName)
                            .orElseGet(() -> categoryRepository.save(new Category(categoryName, "Mô tả cho " + categoryName)));

                    Random random = new Random();
                    int productCount = random.nextInt(8) + 3;

                    for (int i = 0; i < productCount; i++) {
                        String productName = productNames.get(random.nextInt(productNames.size())) + " " + (i + 1);

                        Product product = new Product();
                        product.setName(productName);
                        product.setPrice(BigDecimal.valueOf(100.0 + i * 10));
                        product.setImageUrl("https://via.placeholder.com/500?text=" + productName);
                        product.setCategory(category);
                        productRepository.save(product);
                    }
                }
            }
        }
    }
}
