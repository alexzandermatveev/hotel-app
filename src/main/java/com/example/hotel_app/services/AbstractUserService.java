package com.example.hotel_app.services;

import com.example.hotel_app.dto.UserDtoReading;
import com.example.hotel_app.dto.UserDtoWriting;

import java.util.List;

/**
 * Интерфейс сервиса по работе с {@link  com.example.hotel_app.model.AppUser}
 */
public interface AbstractUserService {
    /**
     * Найти пользователя по идентификатору
     * @param username Идентификатор пользователя
     * @return Найденный пользователь
     */
    UserDtoReading findById(String username);

    /**
     * Создает нового пользователя
     * @param instance DTO для последующей записи, переданное пользователем
     * @param userRole Роль пользователя
     * @return Созданный пользователь
     */
    UserDtoReading createUser(UserDtoWriting instance, String userRole);

    /**
     * Изменяет пользователя
     * @param instance Идентификатор пользователя, которого нужно изменить
     * @param userRole Роль пользователя, которую нужно присвоить
     * @return Измененный пользователь
     */
    UserDtoReading editUser(UserDtoWriting instance, String userRole);

    /**
     * Удаляет пользователя
     * @param username Идентификатор пользователя
     * @return true - если удаление прошло успешно, иначе - false
     */
    Boolean deleteUser(String username);

    /**
     * Возвращает всех пользователей из БД
     * @return Список пользователей
     */
    List<UserDtoReading> getList();
}
