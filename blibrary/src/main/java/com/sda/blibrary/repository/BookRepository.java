package com.sda.blibrary.repository;

import com.sda.blibrary.persistence.model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookModel,Long> {
}
