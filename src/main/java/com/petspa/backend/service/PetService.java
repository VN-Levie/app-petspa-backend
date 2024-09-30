package com.petspa.backend.service;

import com.petspa.backend.dto.PetDTO;
import com.petspa.backend.entity.Pet;
import com.petspa.backend.repository.PetRepository;
import com.petspa.backend.repository.PetTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetTypeRepository petTypeRepository;

    // Lấy tất cả thú cưng
    public List<PetDTO> getAllPets() {
        return petRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy tất cả thú cưng theo phân trang và tìm kiếm
    public List<PetDTO> getAllPets(String petType, String search, int limit, int offset, Long accountId) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Pet> petPage = petRepository.findAllPetsByAccount(petType, search, accountId, pageable);

        return petPage.stream()
                      .map(this::convertToDTO)
                      .collect(Collectors.toList());
    }

    // Lấy một thú cưng theo ID
    public PetDTO getPetById(Long id) {
        return petRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    //lấy thú chưng bằng id và account id
    public PetDTO getPetByIdAndAccountId(Long petId, Long accountId) {
        return petRepository.findById(petId)
                .filter(pet -> pet.getAccountId().equals(accountId))
                .map(this::convertToDTO)
                .orElse(null);
    }

    //check trùng thông tin
    public PetDTO findMatchingPetByAccount(PetDTO petDTO, Long accountId) {
        //check trùng tên, chiều cao, cân nặng và loại thú cưng
        return petRepository.findAllPetsByAccount(null, null, accountId, PageRequest.of(0, 1))
                .stream()
                .filter(pet -> pet.getName().equals(petDTO.getName())
                        && pet.getHeight().equals(petDTO.getHeight())
                        && pet.getWeight().equals(petDTO.getWeight())
                        && pet.getPetType().getId().equals(petDTO.getPetTypeId()))
                .map(this::convertToDTO)
                .findFirst()
                .orElse(null);
    }

    // Thêm thú cưng mới
    public PetDTO addPet(PetDTO petDTO) {
        Pet pet = convertToEntity(petDTO);
        pet = petRepository.save(pet);
        return convertToDTO(pet);
    }

    // Cập nhật thông tin thú cưng
    public PetDTO updatePet(Long id, PetDTO petDTO) {
        Optional<Pet> existingPet = petRepository.findById(id);
        if (existingPet.isPresent()) {
            Pet pet = existingPet.get();
            pet.setName(petDTO.getName());
            pet.setDescription(petDTO.getDescription());
            pet.setHeight(petDTO.getHeight());
            pet.setWeight(petDTO.getWeight());
            pet.setAvatarUrl(petDTO.getAvatarUrl());

            // Cập nhật loại thú cưng (PetType)
            petTypeRepository.findById(petDTO.getPetTypeId())
                             .ifPresent(pet::setPetType);

            pet = petRepository.save(pet);
            return convertToDTO(pet);
        }
        return null;
    }

    // Xóa thú cưng
    public void deletePet(Long id) {
        petRepository.softDelete(id);
    }

    // Chuyển đổi từ Pet sang PetDTO
    private PetDTO convertToDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setName(pet.getName());
        petDTO.setDescription(pet.getDescription());
        petDTO.setHeight(pet.getHeight());
        petDTO.setWeight(pet.getWeight());
        petDTO.setAvatarUrl(pet.getAvatarUrl());
        petDTO.setPetTypeId(pet.getPetType().getId());
        petDTO.setAccountId(pet.getAccountId());
        petDTO.setDeleted(pet.getDeleted());
        return petDTO;
    }

    // Chuyển đổi từ PetDTO sang Pet entity
    private Pet convertToEntity(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setId(petDTO.getId());
        pet.setName(petDTO.getName());
        pet.setDescription(petDTO.getDescription());
        pet.setHeight(petDTO.getHeight());
        pet.setWeight(petDTO.getWeight());
        pet.setAvatarUrl(petDTO.getAvatarUrl());
        pet.setAccountId(petDTO.getAccountId());
        petTypeRepository.findById(petDTO.getPetTypeId())
                         .ifPresent(pet::setPetType);

        return pet;
    }
}
