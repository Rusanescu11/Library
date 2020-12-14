package com.sda.blibrary.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
public class BookModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book", orphanRemoval = false)
    @JsonIgnoreProperties("book")
    private List<ReservationModel> reservation = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "bookModel", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("bookModel")
    private PhotoL photoB;


    public PhotoL getPhotoB() {
        return photoB;
    }

    public void setPhotoB(PhotoL photoB) {
        this.photoB = photoB;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ReservationModel> getReservation() {
        return reservation;
    }

    public void setReservation(List<ReservationModel> reservation) {
        this.reservation = reservation;
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
}
