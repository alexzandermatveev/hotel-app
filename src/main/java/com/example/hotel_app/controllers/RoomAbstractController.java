package com.example.hotel_app.controllers;

import com.example.hotel_app.DTO.DtoForPagination;
import com.example.hotel_app.DTO.RoomDtoReading;
import com.example.hotel_app.DTO.RoomDtoWriting;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

/**
 * Интерфейс контролера по работе с {@link com.example.hotel_app.model.Room}
 */
public interface RoomAbstractController {
    /**
     * Возвращает {@link com.example.hotel_app.model.Room} по id
     * @param id Идентификатор комнаты
     * @return {@code ResponseEntity<RoomDtoReading>}, см. {@link RoomDtoReading}
     */
    ResponseEntity<RoomDtoReading> findById(long id);

    /**
     * Создает новую комнату
     * @param  newRoom {@link RoomDtoWriting} Сущность для записи данных о комнате, передаваемая пользователем
     * @return {@code ResponseEntity<RoomDtoReading>}, см. {@link RoomDtoReading}
     */
    ResponseEntity<RoomDtoReading> makeNewRoom(RoomDtoWriting newRoom);

    /**
     * Изменяет данные о комнате
     * @param roomForEditing newRoom {@link RoomDtoWriting} Сущность для записи данных о комнате, передаваемая пользователем
     * @return {@code ResponseEntity<RoomDtoReading>}, см. {@link RoomDtoReading}
     */
    ResponseEntity<RoomDtoReading> editRoom(RoomDtoWriting roomForEditing);

    /**
     * Удаляет комнату
     * @param id Идентификатор комнаты
     * @return {@code ResponseEntity<String>}
     */
    ResponseEntity<String> deleteRoom(long id);

    /**
     * Возвращает все имеющиеся в БД комнаты
     * @return {@code ResponseEntity<List<RoomDtoReading>>}, см. {@link RoomDtoReading}
     */
    ResponseEntity<List<RoomDtoReading>> getAllRooms();

    /**
     * Возвращает отфильтрованный список комнат, удовлетворяющей параметрам
     * @param id Идентификатор комнаты
     * @param description Описание комнаты
     * @param minPrice Минимальная цена
     * @param maxPrice Максимальная цена
     * @param hotelId Идентификатор отеля
     * @param maxGuests Максимальное количество гостей
     * @param enterDate Дата заезда
     * @param exitDate Дата выезда
     * @param page Номер страницы (для пагинации)
     * @param size Количество элементов на странице (для пагинации)
     * @return {@code ResponseEntity<DtoForPagination<RoomDtoReading>>}, см. {@link DtoForPagination}
     */
    ResponseEntity<DtoForPagination<RoomDtoReading>> getFilteredRooms(Long id, String description, Double minPrice,
                                                                      Double maxPrice, Long hotelId,
                                                                      Integer maxGuests, Date enterDate, Date exitDate,
                                                                      Integer page, Integer size);
}
