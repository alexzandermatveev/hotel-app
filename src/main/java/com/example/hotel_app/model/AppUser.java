package com.example.hotel_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Сущность пользователя
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AppUser {

    /**
     * Имя пользователя, оно же идентификатор
     */
    @Id
    @Column(name = "username")
    private String username;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(nullable = false, name = "email")
    private String email;

    @Column(nullable = false, name = "role")
    private UserRoles role;

    @Column(name = "bookings")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Booking> bookings;

}
