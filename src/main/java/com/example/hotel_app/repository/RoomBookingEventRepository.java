package com.example.hotel_app.repository;

import com.example.hotel_app.model.RoomBookingEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Репозиторий по работе с сущностями {@link RoomBookingEvent}
 */
public interface RoomBookingEventRepository extends MongoRepository<RoomBookingEvent, String> {
}
