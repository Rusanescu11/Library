package com.sda.blibrary.service;

import com.sda.blibrary.persistence.dto.BookDto;

import com.sda.blibrary.persistence.dto.ReservationDto;
import com.sda.blibrary.persistence.model.BookModel;
import com.sda.blibrary.persistence.model.ReservationModel;
import com.sda.blibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;


    public void save(BookDto bookDto) {
        BookModel bookModel = new BookModel();
        bookModel.setId(bookDto.getId());
        bookModel.setTitle(bookDto.getTitle());
        bookModel.setDescription(bookDto.getDescription());
        bookRepository.save(bookModel);

    }

    public List<BookDto> getAll() {
        List<BookModel> bookModelList = bookRepository.findAll();
        List<BookDto> bookDtoList = new ArrayList<>();
        for (BookModel bookModel : bookModelList) {
            BookDto bookDto = new BookDto();
            bookDto.setId(bookModel.getId());
            bookDto.setTitle(bookModel.getTitle());
            bookDto.setDescription(bookModel.getDescription());
            //bookDto.setIdPhoto(bookModel.getPhotoB().getId());
            bookDtoList.add(bookDto);

        }
        return bookDtoList;
    }

    public BookDto getById(long id) {
        Optional<BookModel> bookModelOptional = bookRepository.findById(id);
        BookDto bookDto = new BookDto();
        if (bookModelOptional.isPresent()) {
            BookModel bookModel = bookModelOptional.get();
            bookDto.setId(bookModel.getId());
            bookDto.setTitle(bookModel.getTitle());
            bookDto.setDescription(bookModel.getDescription());
            bookDto.setIdPhoto(bookModel.getPhotoB().getId());
            List<ReservationDto> reservationDtoList = new ArrayList<>();
            for (ReservationModel reservationModel : bookModel.getReservation()) {
                ReservationDto reservationDto = new ReservationDto();
                reservationDto.setStartDate(reservationModel.getStartDate());
                reservationDto.setEndDate(reservationModel.getEndDate());
                reservationDtoList.add(reservationDto);
            }

            bookDto.setReservation(reservationDtoList);
        }
        return bookDto;
    }


    public void delete(long id) {
        bookRepository.deleteById(id);
    }

    public void update(BookDto bookDto) {
        Optional<BookModel> bookModelOptional = bookRepository.findById(bookDto.getId());
        if (bookModelOptional.isPresent()) {
            BookModel bookModel = bookModelOptional.get();
            bookModel.setId(bookDto.getId());
            bookModel.setTitle(bookDto.getTitle());
            bookModel.setDescription(bookDto.getDescription());
            bookRepository.save(bookModel);
        }
    }
}
