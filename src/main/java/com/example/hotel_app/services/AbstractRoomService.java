package com.example.hotel_app.services;

import com.example.hotel_app.DTO.DtoForPagination;
import com.example.hotel_app.DTO.RoomDtoReading;
import com.example.hotel_app.DTO.RoomDtoWriting;

import java.util.Date;
import java.util.List;

/**
 * Интерфейс сервиса для работы с {@link com.example.hotel_app.model.Room}
 */
public interface AbstractRoomService {
    /**
     * Находит комнату по идентификатору
     *
     * @param id Идентификатор комнаты
     * @return Найденная комната {@link RoomDtoReading} в формате DTO для чтения
     */
    RoomDtoReading findByID(long id);

    /**
     * Создает новую комнату
     *
     * @param instance DTO для записи, переданное пользователем
     * @return DTO для чтения
     */
    RoomDtoReading makeOne(RoomDtoWriting instance);

    /**
     * Изменяет параметры комнаты
     *
     * @param instance DTO для записи изменений
     * @return DTO для чтения
     */
    RoomDtoReading editOne(RoomDtoWriting instance);

    /**
     * Удаляет комнату
     *
     * @param id Идентификатор комнаты
     * @return {@link  Boolean} При успешном исходе возвращает true, иначе false
     */
    Boolean deleteOne(long id);

    /**
     * Возвращает лист всех комнат из БД
     *
     * @return Список всех комнат {@code List<RoomDtoReading> }
     */
    List<RoomDtoReading> getList();

    /**
     * Возвращает фильтрованный список комнат, удовлетворяющих параметрам
     *
     * @param id
     * @param description
     * @param minPrice
     * @param maxPrice
     * @param hotelId
     * @param maxGuests
     * @param enterDate
     * @param exitDate
     * @param page
     * @param size
     * @return Фильтрованный список комнат {@code DtoForPagination<RoomDtoReading>}
     * @see com.example.hotel_app.controllers.RoomController#getFilteredRooms(Long, String, Double, Double, Long, Integer, Date, Date, Integer, Integer)
     */
    DtoForPagination<RoomDtoReading> getFilteredRooms(Long id, String description, Double minPrice,
                                                      Double maxPrice, Long hotelId, Integer maxGuests,
                                                      Date enterDate, Date exitDate,
                                                      Integer page, Integer size);
}
