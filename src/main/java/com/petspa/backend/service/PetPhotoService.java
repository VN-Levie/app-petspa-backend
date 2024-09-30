package com.petspa.backend.service;

import com.petspa.backend.dto.PetPhotoDTO;
import com.petspa.backend.entity.PetPhoto;
import com.petspa.backend.repository.PetPhotoRepository;
import com.petspa.backend.repository.PetRepository;
import com.petspa.backend.repository.PetPhotoCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetPhotoService {

    @Autowired
    private PetPhotoRepository petPhotoRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetPhotoCategoryRepository petPhotoCategoryRepository;

    // Lấy tất cả ảnh của thú cưng
    public List<PetPhotoDTO> getAllPhotosByPetId(Long petId) {
        return petPhotoRepository.findByPetId(petId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Thêm ảnh cho thú cưng
    public PetPhotoDTO addPhoto(PetPhotoDTO petPhotoDTO) {
        PetPhoto petPhoto = convertToEntity(petPhotoDTO);
        petPhoto = petPhotoRepository.save(petPhoto);
        return convertToDTO(petPhoto);
    }

    // Xóa ảnh
    public void deletePhoto(Long id) {
        petPhotoRepository.deleteById(id);
    }

    // Chuyển đổi từ PetPhoto sang PetPhotoDTO
    private PetPhotoDTO convertToDTO(PetPhoto petPhoto) {
        PetPhotoDTO petPhotoDTO = new PetPhotoDTO();
        petPhotoDTO.setId(petPhoto.getId());
        petPhotoDTO.setPhotoUrl(petPhoto.getPhotoUrl());
        petPhotoDTO.setPetId(petPhoto.getPet().getId());
        petPhotoDTO.setPhotoCategoryId(petPhoto.getPhotoCategory().getId());
        petPhotoDTO.setUploadedBy(petPhoto.getUploadedBy());
        return petPhotoDTO;
    }

    // Chuyển đổi từ PetPhotoDTO sang PetPhoto entity
    private PetPhoto convertToEntity(PetPhotoDTO petPhotoDTO) {
        PetPhoto petPhoto = new PetPhoto();
        petPhoto.setId(petPhotoDTO.getId());
        petPhoto.setPhotoUrl(petPhotoDTO.getPhotoUrl());

        petRepository.findById(petPhotoDTO.getPetId())
                     .ifPresent(petPhoto::setPet);

        petPhotoCategoryRepository.findById(petPhotoDTO.getPhotoCategoryId())
                                  .ifPresent(petPhoto::setPhotoCategory);

        petPhoto.setUploadedBy(petPhotoDTO.getUploadedBy());

        return petPhoto;
    }
}
