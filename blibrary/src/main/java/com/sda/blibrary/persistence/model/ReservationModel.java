package com.sda.blibrary.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "reservation")
public class ReservationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date startDate;
    private Date endDate;


    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("reservation")
    private BookModel book;


    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("reservation")
    private UserModel user;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public BookModel getBook() {
        return book;
    }

    public void setBook(BookModel book) {
        this.book = book;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
