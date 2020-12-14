package com.sda.blibrary.repository;

import com.sda.blibrary.persistence.model.ReservationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationModel,Long> {
    List<ReservationModel> findAllByUser_Id(long id);
}
