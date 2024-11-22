package com.example.hotel_app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * Контроллер по обработке исключений, которые генерирует приложение в ходе работы
 */
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(HotelNotFound.class)
    public ResponseEntity<String> noHotel(HotelNotFound ex) {
        return new ResponseEntity<>("hotel not found: \n" + ex.getMessage(),
                HttpStatus.valueOf(404));
    }
    @ExceptionHandler(RoomNotFound.class)
    public ResponseEntity<String> noHotel(RoomNotFound ex) {
        return new ResponseEntity<>("room not found: \n" + ex.getMessage(),
                HttpStatus.valueOf(404));
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<String> noHotel(UserNotFound ex) {
        return new ResponseEntity<>("user not found: \n" + ex.getMessage(),
                HttpStatus.valueOf(404));
    }

    @ExceptionHandler(BookingNotFound.class)
    public ResponseEntity<String> noHotel(BookingNotFound ex) {
        return new ResponseEntity<>("booking not found: \n" + ex.getMessage(),
                HttpStatus.valueOf(404));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> noHotel(UsernameNotFoundException ex) {
        return new ResponseEntity<>("Authorization failed: \n" + ex.getMessage(),
                HttpStatus.valueOf(401));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> badRequest(IllegalArgumentException ex) {
        return new ResponseEntity<>("illegal argument was sent:\n" + ex.getMessage(),
                HttpStatus.valueOf(400));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> anotherExceptions(Exception ex) {
        return new ResponseEntity<>("something went wrong:\n" + ex.getMessage(),
                HttpStatus.valueOf(500));
    }
}
