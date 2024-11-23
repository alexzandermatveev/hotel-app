package com.example.hotel_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * Сущность для отчетности по событию создания брони
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class RoomBookingEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Field(name = "username")
    private String username;
    @Field(name = "room_id")
    private Long roomId;
    @Field(name = "enter_date")
    private Date enterDate;
    @Field(name = "exit_date")
    private Date exitDate;
    @Field(name = "timestamp")
    private Date timestamp;
}
