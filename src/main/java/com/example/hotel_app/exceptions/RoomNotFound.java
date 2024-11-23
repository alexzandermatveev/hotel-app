package com.example.hotel_app.exceptions;

/**
 * Исключение по ненахождению сущности {@link com.example.hotel_app.model.Room}
 */
public class RoomNotFound extends IllegalArgumentException  {
    public RoomNotFound(String message){
        super(message);
    }
}
