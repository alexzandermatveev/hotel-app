package com.example.hotel_app.DTOconverter;

import com.example.hotel_app.DTO.HotelDTOForReading;
import com.example.hotel_app.DTO.HotelDTOForWriting;
import com.example.hotel_app.DTO.RoomDtoReading;
import com.example.hotel_app.model.Booking;
import com.example.hotel_app.model.Hotel;
import com.example.hotel_app.model.Room;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
/**
 * Интерфейс мэпера для сущностей {@link Hotel} и его DTO
 */
@Mapper
public interface HotelMapper {
    HotelMapper INSTANCE  = Mappers.getMapper(HotelMapper.class);

    @Mapping(source = "hotel.rooms", target = "rooms", qualifiedByName = "roomsToDto")
    HotelDTOForReading hotelToReader(Hotel hotel, @Context RoomMapper roomMapper);

    Hotel writerToHotel(HotelDTOForWriting writer);

    @Named("roomsToDto")
    default List<RoomDtoReading> roomsToDto(List<Room> value, @Context RoomMapper roomMapper){
        if(value != null && !value.isEmpty()) {
            return value.stream()
                    .map(roomMapper::roomToReader)
                    .toList();
        }
        return List.of();
    }
}
