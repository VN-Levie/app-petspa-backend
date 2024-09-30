package com.petspa.backend.service;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;

@Service
public class FileUploadService {

    private final Environment environment;

    // Thư mục gốc mặc định
    private final String defaultUploadDir = "src/main/resources/static/uploads/";

    public FileUploadService(Environment environment) {
        this.environment = environment;
    }

    /**
     * Phương thức upload file
     * 
     * @param file      file cần upload
     * @param directory thư mục cần upload (có thể null hoặc rỗng)
     * @return URL truy cập file
     */
    public String uploadFile(MultipartFile file, String directory) {
        try {
            // Kiểm tra tên file
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            // lấy extension của file
            String extension = fileName.substring(fileName.lastIndexOf("."));

            // Sử dụng thư mục gốc mặc định nếu không có thư mục truyền vào
            String uploadDir = (directory == null || directory.isEmpty()) ? defaultUploadDir
                    : defaultUploadDir + "/" + directory;

            // Tạo đường dẫn nơi lưu trữ file
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath); // Tạo thư mục nếu chưa tồn tại
            }

            // Tạo tên file an toàn với timestamp
            fileName = System.currentTimeMillis() + "-" + convertFileName(fileName);

            // Lưu file vào đường dẫn
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            // Lấy port và host từ môi trường (có thể cấu hình trong application.properties)
            String port = environment.getProperty("server.port");
            String host = "localhost"; // Hoặc lấy từ environment nếu cần

            // Tạo URL để truy cập file
            String url = "http://" + host + ":" + port + "/uploads/" + directory + "/" + fileName;
            return url;

        } catch (IOException e) {
            throw new RuntimeException("Error saving file", e);
        }
    }

    public String convertFileName(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return fileName;
        }
        //phần trước dấu chấm
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        //phần sau dấu chấm
        String extension = fileName.substring(fileName.lastIndexOf("."));

        // Bước 1: Loại bỏ dấu tiếng Việt
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD);
        String withoutAccent = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // Bước 2: Thay thế tất cả các ký tự không phải là chữ cái, số hoặc dấu gạch
        // ngang bằng dấu gạch ngang
        String replaced = withoutAccent.replaceAll("[^a-zA-Z0-9]+", "-");

        // Bước 3: Loại bỏ dấu gạch ngang dư thừa (nhiều dấu gạch ngang liên tiếp)
        String singleDash = replaced.replaceAll("-{2,}", "-");

        // Bước 4: Loại bỏ dấu gạch ngang ở đầu và cuối file name nếu có
        return singleDash.replaceAll("^-|-$", "").toLowerCase()+extension;
    }
}
