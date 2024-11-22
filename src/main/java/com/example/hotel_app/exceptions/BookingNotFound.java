package com.example.hotel_app.exceptions;

import java.security.InvalidParameterException;

/**
 * Исключение по ненахождению сущности {@link com.example.hotel_app.model.Booking}
 */
public class BookingNotFound extends InvalidParameterException {
    public BookingNotFound(String message){
        super(message);
    }
}
