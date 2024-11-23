package com.example.hotel_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Сущность отеля
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "title_of_advertisement")
    private String titleOfAdvertisement;
    @Column(name = "city")
    private String city;
    @Column(name = "address")
    private String address;
    @Column(name = "distance")
    private double distanceFromCityCenterInMetres;
    @Column(name = "rating")
    private double rating;
    @Column(name = "quantity_of_voices")
    private long quantityOfVoices;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> rooms;

}
