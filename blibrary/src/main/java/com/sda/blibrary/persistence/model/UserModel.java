package com.sda.blibrary.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sda.blibrary.common.util.Hasher;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username;
    private String email;
    private String city;
    private String street;
    private int number;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//nu va permite niciodata cautarea dupa parola sau afisarea acesteia
    private String password;

    @Transient
    @JsonProperty
    private String newPassword;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = false)
    @JsonIgnoreProperties("user")
    private List<ReservationModel> reservation = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @JsonIgnoreProperties("userModel")
    private Role role;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "userModel", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("userModel")
    private PhotoU photou;

    public PhotoU getPhotou() {
        return photou;
    }

    public void setPhotou(PhotoU photou) {
        this.photou = photou;
    }

    public List<ReservationModel> getReservation() {
        return reservation;
    }

    public void setReservation(List<ReservationModel> reservation) {
        this.reservation = reservation;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Hasher.encode(password);//aici folosim metoda: encode din clasa noastra Hasher
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
