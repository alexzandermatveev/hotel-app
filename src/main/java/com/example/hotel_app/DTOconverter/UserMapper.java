package com.example.hotel_app.DTOconverter;

import com.example.hotel_app.DTO.UserDtoReading;
import com.example.hotel_app.DTO.UserDtoWriting;
import com.example.hotel_app.model.AppUser;
import com.example.hotel_app.model.Booking;
import com.example.hotel_app.model.UserRoles;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * Интерфейс мэпера для сущностей {@link AppUser} и его DTO
 */
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "user.role", target = "role", qualifiedByName = "roleToString")
    UserDtoReading userToReader(AppUser user);

    @Mapping(source = "writer.password", target = "password", qualifiedByName = "encodePassword")
    AppUser writerToUser(UserDtoWriting writer, @Context PasswordEncoder passwordEncoder);

    @Named("strToRole")
    default UserRoles strToRole(String stringRole) {
        try {
            return UserRoles.valueOf(stringRole);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("impossible to create user with the role: " + stringRole);
        }
    }

    @Named("roleToString")
    default String roleToString(UserRoles userRoles) {
        if (userRoles != null) {
            return userRoles.toString();
        }
        throw new IllegalArgumentException("impossible to create user with the role");

    }

    @Named("encodePassword")
    default String encodePassword(String password, @Context PasswordEncoder passwordEncoder) {
        if(password != null && !password.isEmpty()){
            return passwordEncoder.encode(password);
        }
        throw new IllegalArgumentException("illegal value for password");
    }

}
