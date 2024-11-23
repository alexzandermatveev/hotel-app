package com.example.hotel_app.config;

import com.example.hotel_app.dtoConverter.BookingMapper;
import com.example.hotel_app.dtoConverter.HotelMapper;
import com.example.hotel_app.dtoConverter.RoomMapper;
import com.example.hotel_app.dtoConverter.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Создает бины мэперов MapStruct с использованием переменной INSTANCE, чтобы сократить последующий код
 */
@Configuration
public class ConfigClass {

    @Bean
    public HotelMapper hotelMapper() {
        return HotelMapper.INSTANCE;
    }

    @Bean
    public RoomMapper roomMapper(){
        return RoomMapper.INSTANCE;
    }

    @Bean
    public UserMapper userMapper(){
        return UserMapper.INSTANCE;
    }

    @Bean
    public BookingMapper bookingMapper(){
        return BookingMapper.INSTANCE;
    }
}
