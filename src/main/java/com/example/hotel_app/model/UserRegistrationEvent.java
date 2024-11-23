package com.example.hotel_app.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * Сущность для отчетности по событию создания пользователя
 */
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Field(name = "username")
    private String username;
    @Field(name = "timestamp")
    private Date timestamp;

}
