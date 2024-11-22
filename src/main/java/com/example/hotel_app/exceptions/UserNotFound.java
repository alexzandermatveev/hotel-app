package com.example.hotel_app.exceptions;

import java.security.InvalidParameterException;

/**
 * Исключение по ненахождению сущности {@link com.example.hotel_app.model.AppUser}
 */
public class UserNotFound extends InvalidParameterException {
    public UserNotFound(String message){
        super(message);
    }
}
