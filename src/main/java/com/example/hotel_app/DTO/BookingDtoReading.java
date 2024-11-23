package com.example.hotel_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO для отображения информации об объекте {@link  com.example.hotel_app.model.Booking}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDtoReading {
    private Long id;
    private Date enterDate;
    private Date exitDate;
    private Long room;
    private String user;
}
