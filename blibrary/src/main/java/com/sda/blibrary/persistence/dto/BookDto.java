package com.sda.blibrary.persistence.dto;

import java.util.List;

public class BookDto {
    private long id;
    private String title;
    private String description;
    private String idPhoto;
    private List<ReservationDto> reservation;

    public List<ReservationDto> getReservation() {
        return reservation;
    }

    public void setReservation(List<ReservationDto> reservation) {
        this.reservation = reservation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }
}
