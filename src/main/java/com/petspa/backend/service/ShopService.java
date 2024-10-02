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

    // Sample data for products
    private final List<String> foodNames = Arrays.asList("Dog Food", "Cat Food", "Dog Snack", "Cat Snack", "Nutritional Seeds", "Dry Food");
    private final List<String> toyNames = Arrays.asList("Toy Ball", "Rubber Chew Bone", "Chew Toy", "Sound Toy", "Tug Rope", "Toy Mouse");
    // Accessories
    private final List<String> accessoryNames = Arrays.asList("Collar", "Harness", "Leash");
    // Clothes
    private final List<String> clothesNames = Arrays.asList("Dog Sweater", "Cat Raincoat", "Dog T-Shirt", "Cat Jacket", "Cat T-Shirt");

    // Khởi tạo dữ liệu cho Shop (Sản phẩm và danh mục)
    public void initializeShopData() {
        if (productRepository.findAll().isEmpty()) {
            List<Map<String, List<String>>> categories = new ArrayList<>();

            categories.add(Map.of("Food", foodNames));
            categories.add(Map.of("Toy", toyNames));
            categories.add(Map.of("Accessory", accessoryNames));
            categories.add(Map.of("Clothes", clothesNames));

            for (Map<String, List<String>> categoryMap : categories) {
                for (Map.Entry<String, List<String>> entry : categoryMap.entrySet()) {
                    String categoryName = entry.getKey();
                    List<String> productNames = entry.getValue();

                    Category category = categoryRepository.findByName(categoryName)
                            .orElseGet(() -> categoryRepository.save(new Category(categoryName, "Description for " + categoryName)));

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
