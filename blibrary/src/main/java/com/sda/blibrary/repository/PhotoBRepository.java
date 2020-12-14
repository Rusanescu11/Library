package com.sda.blibrary.repository;

import com.sda.blibrary.persistence.model.PhotoL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoBRepository extends JpaRepository<PhotoL, String> {
}
