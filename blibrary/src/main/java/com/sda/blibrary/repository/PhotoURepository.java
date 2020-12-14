package com.sda.blibrary.repository;

import com.sda.blibrary.persistence.model.PhotoU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoURepository extends JpaRepository<PhotoU, String> {
}
