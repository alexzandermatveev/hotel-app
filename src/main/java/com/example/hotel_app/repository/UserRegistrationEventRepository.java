package com.example.hotel_app.repository;

import com.example.hotel_app.model.UserRegistrationEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий по работе с сущностями {@link UserRegistrationEvent}
 */
@Repository
public interface UserRegistrationEventRepository extends MongoRepository<UserRegistrationEvent, String> {
}
