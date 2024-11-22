package com.example.hotel_app.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO для отображения сущности {@link com.example.hotel_app.model.Hotel}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTOForReading {

    private long id;
    private String name;
    private String titleOfAdvertisement;
    private String city;
    private String address;
    private double distanceFromCityCenterInMetres;
    private double rating;
    private long quantityOfVoices;

    private List<RoomDtoReading> rooms;
}
