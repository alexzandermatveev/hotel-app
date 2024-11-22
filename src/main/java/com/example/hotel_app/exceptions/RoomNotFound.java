package com.example.hotel_app.exceptions;

import java.security.InvalidParameterException;
/**
 * Исключение по ненахождению сущности {@link com.example.hotel_app.model.Room}
 */
public class RoomNotFound extends InvalidParameterException {
    public RoomNotFound(String message){
        super(message);
    }
}
