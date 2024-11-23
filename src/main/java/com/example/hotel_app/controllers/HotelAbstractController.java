package com.example.hotel_app.controllers;

import com.example.hotel_app.dto.HotelDTOForReading;
import com.example.hotel_app.dto.HotelDTOForWriting;
import com.example.hotel_app.dto.DtoForPagination;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Интерфейс контроллера по работе с {@link com.example.hotel_app.model.Hotel}
 */
public interface HotelAbstractController {
    /**
     * Возвращает отель по идентификатору
     * @param id Идентификатор отеля
     * @return {@code ResponseEntity<HotelDTOForReading>} см. {@link HotelDTOForReading}
     */
    ResponseEntity<HotelDTOForReading> findById(long id);

    /**
     * Создает новый отель
     * @param newHotel DTO передаваемый пользователем для записи
     * @return {@code ResponseEntity<HotelDTOForReading>} см. {@link HotelDTOForReading}
     */
    ResponseEntity<HotelDTOForReading> makeNewHotel(HotelDTOForWriting newHotel);

    /**
     * Изменяет отель
     * @param hotelForEditing DTO с нвыми параметрами отеля {@link HotelDTOForWriting}
     * @return {@code ResponseEntity<HotelDTOForReading>} см. {@link HotelDTOForReading}
     */
    ResponseEntity<HotelDTOForReading> editHotel(HotelDTOForWriting hotelForEditing);

    /**
     * Удаляет отель
     * @param id Идентификатор отеля
     * @return {@code ResponseEntity<HotelDTOForReading>}
     */
    ResponseEntity<String> deleteHotel(long id);

    /**
     * Возвращает список всех отелей
     * @return {@code ResponseEntity<List<HotelDTOForReading>>} см. {@link HotelDTOForReading}
     */
    ResponseEntity<List<HotelDTOForReading>> getAllHotels();

    /**
     * Изменяет рейтинг отеля
     * @param hotelId Идентификатор отеля
     * @param newMark Целочисленная оценка от 1 до 5
     * @return {@code ResponseEntity<String>} Итоговое сообщение операции изменения рейтинга отеля
     */
    ResponseEntity<String> changeHotelRating(Long hotelId, Integer newMark);

    /**
     * Возвращает фильтрованный список отелей, удовлетворяющий параметрам
     * @param id Идентификатор отелей
     * @param name Название отеля
     * @param title Заголовок
     * @param city Город, где расположен отель
     * @param address Адрес, где расположен отель
     * @param distanceFromCityCenterInMetres Расстояние от отеля до центра города
     * @param rating Рейтинг отеля
     * @param quantityOfVices Количество отзывов
     * @param page Номер страницы (для пагинации)
     * @param size Количество элементов на странице (для пагинации)
     * @return {@code ResponseEntity<DtoForPagination>}, см. {@link DtoForPagination}
     */
    ResponseEntity<DtoForPagination> getFilteredHotels(Long id, String name, String title,
                                                       String city, String address,
                                                       Double distanceFromCityCenterInMetres, Double rating, Long quantityOfVices,
                                                       Integer page, Integer size);
}
