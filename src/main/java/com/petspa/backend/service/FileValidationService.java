package com.petspa.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
public class FileValidationService {

    // Giới hạn kích thước file là 5MB (5 * 1024 * 1024 byte)
    private static final long MAX_FILE_SIZE = 50 * 1024 * 1024;

    // Danh sách các loại MIME hợp lệ cho file ảnh
    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif", "image/jpg", "image/bmp", "image/webp");

    // Danh sách các phần mở rộng hợp lệ
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "webp");

    public boolean isImageValid(MultipartFile file) {
        try {
            // Kiểm tra kích thước file
            if (file.getSize() > MAX_FILE_SIZE) {
                return false; // File quá lớn
            }

            // Kiểm tra MIME type của file
            String mimeType = file.getContentType();
            if (!ALLOWED_MIME_TYPES.contains(mimeType)) {
                return false; // MIME type không hợp lệ
            }

            // Kiểm tra file có phải là ảnh
            if (!mimeType.startsWith("image")) {
                return false; // File không phải là ảnh
            }

            // Kiểm tra phần mở rộng của file
            String fileName = file.getOriginalFilename();
            if (fileName != null && !isExtensionValid(fileName)) {
                return false; // Phần mở rộng không hợp lệ
            }

            // Nếu vượt qua tất cả các kiểm tra
            return true;

        } catch (Exception e) {
            return false; // Nếu có lỗi, coi như không hợp lệ
        }
    }

    // Kiểm tra phần mở rộng file
    private boolean isExtensionValid(String fileName) {
        String extension = getFileExtension(fileName);
        return ALLOWED_EXTENSIONS.contains(extension.toLowerCase());
    }

    // Lấy phần mở rộng từ tên file
    private String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf('.');
        return (lastIndexOfDot == -1) ? "" : fileName.substring(lastIndexOfDot + 1);
    }
}
