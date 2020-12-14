package com.sda.blibrary.controller;

import com.sda.blibrary.persistence.dto.ReservationDto;

import com.sda.blibrary.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class ReservationController {
    @Autowired
    private ReservationService reservationService;


    @PostMapping("/reservation/{id}")
    public void add(@RequestBody ReservationDto reservationDto, @PathVariable(name = "id") Long id) {
        reservationService.save(reservationDto, id);
    }

    @GetMapping("/reservation")
    public List<ReservationDto> getAll() {
        return reservationService.findAll();
    }

    @GetMapping("/user/user/{id}")
    public List<ReservationDto> findReservationsUser(@PathVariable(name = "id") Long id) {
        return reservationService.findReservationsUser(id);
    }
}
