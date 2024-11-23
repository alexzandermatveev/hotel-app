package com.example.hotel_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Сущность брони
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "enter_date")
    private Date enterDate;
    @Column(name = "exit_date")
    private Date exitDate;

    @ManyToOne
    @JoinColumn(nullable = false, name = "room")
    private Room room;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user")
    private AppUser user;
}
