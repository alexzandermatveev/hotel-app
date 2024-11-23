package com.example.hotel_app.dtoConverter;

import com.example.hotel_app.dto.BookingDtoReading;
import com.example.hotel_app.dto.BookingDtoWriter;
import com.example.hotel_app.model.AppUser;
import com.example.hotel_app.model.Booking;
import com.example.hotel_app.model.Room;
import com.example.hotel_app.repository.RoomRepository;
import com.example.hotel_app.repository.UserRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Интерфейс мэпера для сущностей {@link Booking} и его DTO
 */
@Mapper
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    @Mapping(source = "user", target = "user", qualifiedByName = "userToUsername")
    @Mapping(source = "room", target = "room", qualifiedByName = "roomToId")
    BookingDtoReading bookingToReader(Booking booking);

    @Mapping(source = "user", target = "user", qualifiedByName = "usernameToUser")
    @Mapping(source = "room", target = "room", qualifiedByName = "idToRoom")
    Booking writerToBooking(BookingDtoWriter writer,
                            @Context UserRepository userRepository,
                            @Context RoomRepository roomRepository);

    @Named("userToUsername")
    default String userToUsername(AppUser user) {
        if (user != null) {
            return user.getUsername();
        }
        throw new IllegalArgumentException("impossible to create booking with the user");
    }

    @Named("roomToId")
    default Long roomToId(Room room) {
        if(room != null){
            return room.getId();
        }
        throw new IllegalArgumentException("impossible to create booking with the room");
    }

    @Named("usernameToUser")
    default AppUser usernameToUser(String username, @Context UserRepository userRepository) {
        if (userRepository.existsByUsername(username)) {
            return userRepository.findByUsername(username);
        }
        throw new IllegalArgumentException("impossible to create booking with the username: " + username);
    }

    @Named("idToRoom")
    default Room idToRoom(Long id, @Context RoomRepository roomRepository){
        if(roomRepository.existsById(id)){
            return roomRepository.findById(id).get();
        }
        throw new IllegalArgumentException("impossible to create booking with the room id: " + id);
    }
}
