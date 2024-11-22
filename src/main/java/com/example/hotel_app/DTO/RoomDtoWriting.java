package com.example.hotel_app.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * DTO для последующей записи {@link com.example.hotel_app.model.Room}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDtoWriting {
    private Long id;
    private String name;
    private String description;
    private String roomNumber;
    private Double price;
    private Integer maxPeople;
    private List<Date> occupatedDates;
    private String hotel;
}
