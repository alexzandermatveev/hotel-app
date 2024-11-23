package com.example.hotel_app.exceptions;

/**
 * Исключение по ненахождению сущности {@link com.example.hotel_app.model.AppUser}
 */
public class UserNotFound extends IllegalArgumentException  {
    public UserNotFound(String message){
        super(message);
    }
}
