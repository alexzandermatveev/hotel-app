package com.example.hotel_app.controllers;

import com.example.hotel_app.dto.UserDtoReading;
import com.example.hotel_app.dto.UserDtoWriting;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Интерфейс контроллера для работы с {@link com.example.hotel_app.model.AppUser}
 */
public interface UserAbstractController {
    /**
     * Находит пользователя по идентификатору
     * @param username Идентификатор пользователя (имя пользователя)
     * @return {@code ResponseEntity<UserDtoReading> }, см. {@link UserDtoReading}
     */
    ResponseEntity<UserDtoReading> findById(String username);

    /**
     * Создает нового пользователя
     * @param newUser {@link UserDtoWriting} DTO для записи нового пользователя
     * @param userRole Роль пользователя (USER/ADMIN)
     * @return {@code ResponseEntity<UserDtoReading> }, см. {@link UserDtoReading}
     */
    ResponseEntity<UserDtoReading> createNewUser(UserDtoWriting newUser, String userRole);

    /**
     * Изменяет пользователя
     * @param userForEditing {@link UserDtoWriting} DTO для записи нового пользователя
     * @param userRole  Роль пользователя (USER/ADMIN)
     * @return {@code ResponseEntity<UserDtoReading> }, см. {@link UserDtoReading}
     */
    ResponseEntity<UserDtoReading> editUser(UserDtoWriting userForEditing, String userRole);

    /**
     * Удаляет пользователя
     * @param username Имя пользователя
     * @return {@code ResponseEntity<String> }
     */
    ResponseEntity<String> deleteUser(String username);

    /**
     * Возвращает список пользователей имеющихся в БД
     * @return {@code ResponseEntity<List<UserDtoReading>> }, см. {@link UserDtoReading}
     */
    ResponseEntity<List<UserDtoReading>> getAllUsers();
}
