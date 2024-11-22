package com.example.hotel_app.services;

import com.example.hotel_app.DTO.BookingDtoReading;
import com.example.hotel_app.DTO.BookingDtoWriter;

import java.util.List;

/**
 * Интерфейс для сервиса по работе с {@link com.example.hotel_app.model.Booking}
 */
public interface AbstractBookingService {
    /**
     * Возвращает список из всез {@link com.example.hotel_app.model.Booking}
     * @return {@code List<BookingDtoReading>}
     */
    List<BookingDtoReading> findAll();

    /**
     * Создает бронь комнаты
     * @param writer {@link  BookingDtoWriter}
     * @return {@link BookingDtoReading}
     */
    BookingDtoReading bookRoom(BookingDtoWriter writer);

    /**
     * Изменяет параметры брони
     * @param writer {@link  BookingDtoWriter}
     * @param id Идентификатор брони
     * @return {@link BookingDtoReading}
     */
    BookingDtoReading editBooking(BookingDtoWriter writer, Long id);

    /**
     * Удаляет бронь
     * @param id Идентификатор брони
     * @return {@code boolean}
     */
    boolean deleteBooking(Long id);

    /**
     * Возвращает бронь по идентификатору
     * @param id Идентификатор брони
     * @return {@link BookingDtoReading}
     */
    BookingDtoReading findById(Long id);

}
