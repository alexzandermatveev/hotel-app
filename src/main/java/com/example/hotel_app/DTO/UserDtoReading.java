package com.example.hotel_app.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для вывода сущности {@link com.example.hotel_app.model.AppUser}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoReading {
    private String username;
    private String email;
    private String role;
}
