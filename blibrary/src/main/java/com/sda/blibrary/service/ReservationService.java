package com.sda.blibrary.service;

import com.sda.blibrary.persistence.dto.BookDto;
import com.sda.blibrary.persistence.dto.ReservationDto;
import com.sda.blibrary.persistence.dto.UserDto;
import com.sda.blibrary.persistence.model.BookModel;
import com.sda.blibrary.persistence.model.ReservationModel;
import com.sda.blibrary.persistence.model.UserModel;
import com.sda.blibrary.repository.BookRepository;
import com.sda.blibrary.repository.ReservationRepository;
import com.sda.blibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookService bookService;

    public void save(ReservationDto reservationDto, long idBook) {
        BookDto bookDto = bookService.getById(idBook);
        ReservationModel reservationModel = new ReservationModel();
        reservationModel.setId(reservationDto.getId());
        reservationModel.setStartDate(reservationDto.getStartDate());
        reservationModel.setEndDate(reservationDto.getEndDate());
        System.out.println(reservationDto.getUser().getEmail());
        Optional<UserModel> userModelOptional = userRepository.findById(reservationDto.getUser().getId());
        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();
            reservationModel.setUser(userModel);
        }
        Optional<BookModel> optionalBookModel = bookRepository.findById(idBook);
        if (optionalBookModel.isPresent()) {
            BookModel bookModel = optionalBookModel.get();
            reservationModel.setBook(bookModel);
        }
        if (bookDto.getReservation().size() > 0) {
            if (!checkBookReservation(bookDto, reservationDto)) {
                reservationRepository.save(reservationModel);
            }
        } else {
            reservationRepository.save(reservationModel);
        }
    }

    public List<ReservationDto> findAll() {
        List<ReservationModel> reservationModelList = reservationRepository.findAll();
        List<ReservationDto> reservationDtoList = new ArrayList<>();
        for (ReservationModel reservationModel : reservationModelList) {
            ReservationDto reservationDto = new ReservationDto();
            reservationDto.setId(reservationModel.getId());
            reservationDto.setStartDate(reservationModel.getStartDate());
            reservationDto.setEndDate(reservationModel.getEndDate());
            BookDto bookDto = new BookDto();
            bookDto.setId(reservationModel.getBook().getId());
            bookDto.setTitle(reservationModel.getBook().getTitle());
            bookDto.setDescription(reservationModel.getBook().getDescription());
            reservationDto.setBook(bookDto);
            UserDto userDto = new UserDto();
            userDto.setId(reservationModel.getUser().getId());
            userDto.setUsername(reservationModel.getUser().getUsername());
            userDto.setEmail(reservationModel.getUser().getEmail());
            reservationDto.setUser(userDto);
            reservationDtoList.add(reservationDto);
        }
        return reservationDtoList;
    }

    public List<ReservationDto> findReservationsUser(long id) {
        List<ReservationModel> reservationModelList = reservationRepository.findAllByUser_Id(id);//aici gaseste toate rezarvarile unui anumit user pe care l am ales dupa id
        List<ReservationDto> reservationDtoList = new ArrayList<>();// am creat o lista de rezervariDto goala
        for (ReservationModel reservationModel : reservationModelList) {//am rulat toate rezervarile
            ReservationDto reservationDto = new ReservationDto(); //am creat o RezervareDto noua
            reservationDto.setId(reservationModel.getId());//si am populat o cu atributele din model(backend)
            reservationDto.setStartDate(reservationModel.getStartDate());
            reservationDto.setEndDate(reservationModel.getEndDate());
            BookDto bookDto = new BookDto();//am creat o cartedto noua
            BookModel bookModel = reservationModel.getBook();//am pus in bookModel toate rezarvarile cartilor rulate in for
            bookDto.setId(bookModel.getId());
            bookDto.setTitle(bookModel.getTitle());
            bookDto.setDescription(bookModel.getDescription());
            reservationDto.setBook(bookDto);//am setat rezervarii cartea cu atributele dto
            reservationDtoList.add(reservationDto);//am salvat rezervarea cu tot cu cartea ei in listaDto
        }
        return reservationDtoList;
    }

    public boolean checkReservation(ReservationDto reservationDto, ReservationDto old) {
        if (reservationDto.getStartDate().toLocalDate().isEqual(old.getStartDate().toLocalDate()) &&
                reservationDto.getEndDate().toLocalDate().isEqual(old.getEndDate().toLocalDate())) {
            return true;
        }
        if (reservationDto.getStartDate().before(old.getStartDate())
                && reservationDto.getEndDate().before(old.getEndDate())) {
            return true;
        }
        if (reservationDto.getStartDate().after(old.getStartDate()) &&
                reservationDto.getEndDate().before(old.getEndDate())) {
            return true;

        }
        return false;
    }

    private boolean checkBookReservation(BookDto bookDto, ReservationDto noua) {
        for (ReservationDto old : bookDto.getReservation()) {
            if (checkReservation(noua, old)) {
                return true;
            }
        }
        return false;
    }
}
