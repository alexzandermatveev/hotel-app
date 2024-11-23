package com.example.hotel_app.services;

import com.example.hotel_app.dto.DtoForPagination;
import com.example.hotel_app.dto.HotelDTOForReading;
import com.example.hotel_app.dto.HotelDTOForWriting;

import java.util.List;

/**
 * Интерфейс сервиса по работе с {@link com.example.hotel_app.model.Hotel}
 */
public interface AbstractHotelService {

    /**
     * Возвращает отель по идентификатору
     *
     * @param id Идентификатор отеля
     * @return {@link HotelDTOForReading} DTO для чтения
     */
    HotelDTOForReading findByID(long id);

    /**
     * Создает отель
     *
     * @param instance {@link HotelDTOForWriting} DTO для записи
     * @return {@link HotelDTOForReading}
     */
    HotelDTOForReading makeOne(HotelDTOForWriting instance);

    /**
     * Изменяет параметры отеля
     *
     * @param instance {@link HotelDTOForWriting} DTO для записи
     * @return {@link HotelDTOForReading} DTO для чтения
     */
    HotelDTOForReading editOne(HotelDTOForWriting instance);

    /**
     * Удаляет отель
     *
     * @param id Идентификатор отеля
     * @return {@link Boolean}
     */
    Boolean deleteOne(long id);

    /**
     * Возвращает список всех отелей
     *
     * @return {@code List<HotelDTOForReading>}
     */
    List<HotelDTOForReading> getList();

    /**
     * Изменяет рейтинг указанного отеля
     *
     * @param hotelId Идентификатор отеля
     * @param newMark Новая оценка от 1 до 5
     * @return {@link String}
     */
    String changeHotelRating(Long hotelId, Integer newMark);

    /**
     * Возвращает фильтрованный список в соответствии с параметрами
     * @see com.example.hotel_app.controllers.HotelController#getFilteredHotels(Long, String, String, String, String, Double, Double, Long, Integer, Integer)
     * @return {@code DtoForPagination<HotelDTOForReading>}
     */
    DtoForPagination<HotelDTOForReading> getSpecificHotels(Long id, String name, String title,
                                                           String city, String address,
                                                           Double distanceFromCityCenterInMetres, Double rating,
                                                           Long quantityOfVices,
                                                           Integer page, Integer size);
}
