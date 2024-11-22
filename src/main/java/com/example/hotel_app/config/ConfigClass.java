package com.example.hotel_app.config;

import com.example.hotel_app.DTOconverter.BookingMapper;
import com.example.hotel_app.DTOconverter.HotelMapper;
import com.example.hotel_app.DTOconverter.RoomMapper;
import com.example.hotel_app.DTOconverter.UserMapper;
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
