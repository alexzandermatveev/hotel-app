package com.example.hotel_app.controllers;

import com.example.hotel_app.dto.BookingDtoReading;
import com.example.hotel_app.dto.BookingDtoWriter;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Интерфейс контроллера по работе с {@link com.example.hotel_app.model.Booking}
 */
public interface BookingAbstractController {

    /**
     * Находит заданную бронь
     * @param id Идентификатор брони
     * @return {@code ResponseEntity<BookingDtoReading>} DTO сущности {@link com.example.hotel_app.model.Booking} для вывода, обернутая в {@link ResponseEntity}
     */
    ResponseEntity<BookingDtoReading> findById(Long id);

    /**
     * Создает новую бронь
     * @param newBooking {@link BookingDtoWriter} - DTO сущности {@link com.example.hotel_app.model.Booking} для записи, переданная пользователем
     * @return {@code ResponseEntity<BookingDtoReading>} DTO сущности {@link com.example.hotel_app.model.Booking} для вывода, обернутая в {@link ResponseEntity}
     */
    ResponseEntity<BookingDtoReading> makeNewBooking(BookingDtoWriter newBooking);

    /**
     * Изменяет существующую бронь
     * @param bookingForEditing {@link BookingDtoWriter} - DTO сущности {@link com.example.hotel_app.model.Booking} для записи, переданная пользователем
     * @param id Идентификатор существующей брони, которую нужно изменить
     * @return {@code ResponseEntity<BookingDtoReading>} DTO сущности {@link com.example.hotel_app.model.Booking} для вывода, обернутая в {@link ResponseEntity}
     */
    ResponseEntity<BookingDtoReading> editBooking(BookingDtoWriter bookingForEditing, Long id);

    /**
     * Удаляет бронь
     * @param id Идентификатор брони
     * @return {@code ResponseEntity<String>} - {@link ResponseEntity} c сообщением о результате
     */
    ResponseEntity<String> deleteBooking(Long id);

    /**
     * Возвращает все брони
     * @return {@code ResponseEntity<List<BookingDtoReading>>} - Список всех существующих броней в формате для вывода
     */
    ResponseEntity<List<BookingDtoReading>> getAllBooking();
}
