package com.example.hotel_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Сущность комнаты
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "room_number")
    private String roomNumber;
    @Column(name = "price")
    private Double price;
    @Column(name = "max_people")
    private Integer maxPeople;
    @Column(name = "occupated_dates")
    private List<Date> occupatedDates;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @Column(name = "bookings")
    private List<Booking> bookings;

    @ManyToOne
    @JoinColumn(name = "hotel", nullable = false)
    private Hotel hotel;

}
