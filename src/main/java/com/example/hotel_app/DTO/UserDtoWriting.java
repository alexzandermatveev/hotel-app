package com.example.hotel_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для последующей записи {@link com.example.hotel_app.model.AppUser}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoWriting {

    private String username;

    private String password;

    private String email;
}
