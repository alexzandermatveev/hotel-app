package com.example.hotel_app.exceptions;

/**
 * Исключение по ненахождению сущности {@link com.example.hotel_app.model.Hotel}
 */
public class HotelNotFound extends IllegalArgumentException  {

    public HotelNotFound(String message){
        super(message);
    }
}
