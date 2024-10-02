package com.petspa.backend.repository;

import com.petspa.backend.entity.AddressBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressBookRepository extends JpaRepository<AddressBook, Long> {
    // Lấy danh sách địa chỉ theo accountId
    List<AddressBook> findByAccountId(Long accountId);

    //count address by accountId
    Long countByAccountId(Long accountId);
}
