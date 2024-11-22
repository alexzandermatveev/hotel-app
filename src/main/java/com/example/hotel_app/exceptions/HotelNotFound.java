package com.example.hotel_app.exceptions;

import java.security.InvalidParameterException;

/**
 * Исключение по ненахождению сущности {@link com.example.hotel_app.model.Hotel}
 */
public class HotelNotFound extends InvalidParameterException {

    public HotelNotFound(String message){
        super(message);
    }
}
