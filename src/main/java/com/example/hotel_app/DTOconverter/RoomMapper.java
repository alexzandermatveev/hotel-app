package com.example.hotel_app.DTOconverter;

import com.example.hotel_app.DTO.RoomDtoReading;
import com.example.hotel_app.DTO.RoomDtoWriting;
import com.example.hotel_app.exceptions.HotelNotFound;
import com.example.hotel_app.model.Booking;
import com.example.hotel_app.model.Hotel;
import com.example.hotel_app.model.Room;
import com.example.hotel_app.repository.HotelRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
/**
 * Интерфейс мэпера для сущностей {@link Room} и его DTO
 */
@Mapper
public interface RoomMapper {

    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    @Mapping(source = "room.hotel", target = "hotel", qualifiedByName = "hotelToName")
    RoomDtoReading roomToReader(Room room);

    @Mapping(source = "writer.hotel", target = "hotel", qualifiedByName = "nameToHotel")
    Room writerToRoom(RoomDtoWriting writer, @Context HotelRepository hotelRepository);



    @Named("hotelToName")
    default String hotelToString(Hotel hotel){
        return hotel != null ? hotel.getName() : null;
    }
    @Named("nameToHotel")
    default Hotel nameToHotel(String hotelName, @Context HotelRepository hotelRepository){
        if(hotelRepository.existsByName(hotelName)){
            return hotelRepository.findByName(hotelName);
        }
        throw new HotelNotFound("no hotel with name: " + hotelName);
    }
}
