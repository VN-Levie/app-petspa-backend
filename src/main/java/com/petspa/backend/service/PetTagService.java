package com.petspa.backend.service;

import com.petspa.backend.dto.PetTagDTO;
import com.petspa.backend.entity.PetTag;
import com.petspa.backend.repository.PetTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetTagService {

    @Autowired
    private PetTagRepository petTagRepository;

    // Lấy tất cả loại bảng tên thú cưng
    public List<PetTagDTO> getAllPetTags() {
        return petTagRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Thêm bảng tên thú cưng mới
    public PetTagDTO addPetTag(PetTagDTO petTagDTO) {
        PetTag petTag = new PetTag();
        petTag.setName(petTagDTO.getName());
        petTag.setDescription(petTagDTO.getDescription());
        petTag.setIconUrl(petTagDTO.getIconUrl());
        petTag.setPrice(petTagDTO.getPrice());

        PetTag savedPetTag = petTagRepository.save(petTag);
        return convertToDTO(savedPetTag);
    }

    // Cập nhật bảng tên thú cưng
    public PetTagDTO updatePetTag(Long id, PetTagDTO petTagDTO) {
        Optional<PetTag> existingPetTag = petTagRepository.findById(id);

        if (existingPetTag.isPresent()) {
            PetTag petTag = existingPetTag.get();
            petTag.setName(petTagDTO.getName());
            petTag.setDescription(petTagDTO.getDescription());
            petTag.setPrice(petTagDTO.getPrice());

            if (petTagDTO.getIconUrl() != null) {
                petTag.setIconUrl(petTagDTO.getIconUrl());
            }

            PetTag updatedPetTag = petTagRepository.save(petTag);
            return convertToDTO(updatedPetTag);
        } else {
            return null;
        }
    }

    // Xóa bảng tên thú cưng
    public void deletePetTag(Long id) {
        petTagRepository.softDelete(id);
    }

    // Lấy bảng tên thú cưng theo tên
    public PetTagDTO getPetTagByName(String name) {
        return petTagRepository.findByName(name)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // Lấy bảng tên thú cưng theo ID
    public PetTagDTO getPetTagById(Long id) {
        return petTagRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // Chuyển đổi từ PetTag sang PetTagDTO
    private PetTagDTO convertToDTO(PetTag petTag) {
        PetTagDTO dto = new PetTagDTO();
        dto.setId(petTag.getId());
        dto.setName(petTag.getName());
        dto.setDescription(petTag.getDescription());
        dto.setIconUrl(petTag.getIconUrl());
        dto.setPrice(petTag.getPrice());
        return dto;
    }

    // Chuyển đổi từ PetTagDTO sang PetTag
    private PetTag convertToEntity(PetTagDTO petTagDTO) {
        PetTag petTag = new PetTag();
        petTag.setId(petTagDTO.getId());
        petTag.setName(petTagDTO.getName());
        petTag.setDescription(petTagDTO.getDescription());
        petTag.setIconUrl(petTagDTO.getIconUrl());
        petTag.setPrice(petTagDTO.getPrice());
        return petTag;
    }
}
