package com.example.hotel_app.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO для последующего формирования {@link com.example.hotel_app.model.Booking}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDtoWriter {

    private Date enterDate;
    private Date exitDate;
    private Long room;
    private String user;
}
