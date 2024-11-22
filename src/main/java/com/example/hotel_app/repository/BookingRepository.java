package com.example.hotel_app.repository;

import com.example.hotel_app.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий по работе с сущностями {@link Booking}
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
