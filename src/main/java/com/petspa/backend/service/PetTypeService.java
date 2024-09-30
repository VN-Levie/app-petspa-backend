package com.petspa.backend.service;

import com.petspa.backend.dto.PetTypeDTO;
import com.petspa.backend.entity.Pet;
import com.petspa.backend.entity.PetType;
import com.petspa.backend.repository.PetTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.core.env.Environment;
@Service
public class PetTypeService {

    @Autowired
    private PetTypeRepository petTypeRepository;

    @Autowired
	private Environment environment;

    // Lấy tất cả loại thú cưng
    public List<PetTypeDTO> getAllPetTypes() {
        return petTypeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

   
    // Thêm loại thú cưng mới
    public PetTypeDTO addPetType(PetTypeDTO petTypeDTO) {
        
        
       
        PetType petType = new PetType();
        petType.setName(petTypeDTO.getName());
        petType.setDescription(petTypeDTO.getDescription());
        petType.setIconUrl(petTypeDTO.getIconUrl());

        PetType savedPetType = petTypeRepository.save(petType);
        return convertToDTO(savedPetType);
    }

    // Chuyển đổi từ PetType sang PetTypeDTO
    private PetTypeDTO convertToDTO(PetType petType) {
        PetTypeDTO dto = new PetTypeDTO();
        dto.setId(petType.getId());
        dto.setName(petType.getName());
        dto.setDescription(petType.getDescription());
        dto.setIconUrl(petType.getIconUrl());
        return dto;
    }

    

    // Lấy loại thú cưng theo tên
    public PetTypeDTO getPetTypeByName(String name) {
        return petTypeRepository.findByName(name)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public PetTypeDTO updatePetType(Long id, PetTypeDTO petTypeDTO) {
        Optional<PetType> existingPetType = petTypeRepository.findById(id);
        
        if (existingPetType.isPresent()) {
            PetType petType = existingPetType.get();
            petType.setName(petTypeDTO.getName());
            petType.setDescription(petTypeDTO.getDescription());
    
            // Nếu có URL của icon, thì cập nhật iconUrl
            if (petTypeDTO.getIconUrl() != null) {
                petType.setIconUrl(petTypeDTO.getIconUrl());
            }
    
            PetType updatedPetType = petTypeRepository.save(petType);
            return convertToDTO(updatedPetType);
        } else {
            return null;
        }
    }

    // Xóa loại thú cưng
    public void deletePetType(Long id) {
        petTypeRepository.softDelete(id);
    }

    // Lấy loại thú cưng theo ID
    public PetTypeDTO getPetTypeById(Long id) {
        return petTypeRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

}
