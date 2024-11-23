package com.example.hotel_app.exceptions;

/**
 * Исключение по ненахождению сущности {@link com.example.hotel_app.model.Booking}
 */
public class BookingNotFound extends IllegalArgumentException {
    public BookingNotFound(String message){
        super(message);
    }
}
