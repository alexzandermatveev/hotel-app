package com.example.hotel_app.dto;

import lombok.*;

/**
 * DTO для последующей записи {@link com.example.hotel_app.model.Hotel}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HotelDTOForWriting {
    private Long id;
    private String name;
    private String titleOfAdvertisement;
    private String city;
    private String address;
    private Double distanceFromCityCenterInMetres;

}
