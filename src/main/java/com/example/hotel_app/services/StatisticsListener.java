package com.example.hotel_app.services;

import com.example.hotel_app.model.RoomBookingEvent;
import com.example.hotel_app.model.UserRegistrationEvent;
import com.example.hotel_app.repository.RoomBookingEventRepository;
import com.example.hotel_app.repository.UserRegistrationEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Сервис по обработке сообщений из Kafka
 */
@Service
@RequiredArgsConstructor
public class StatisticsListener {

    private final UserRegistrationEventRepository userRegistrationEventRepository;
    private final RoomBookingEventRepository roomBookingEventRepository;

    /**
     * Сохраняет в БД информации о созданном пользователе
     * @param event Объект информации о создании пользователя
     */
    @KafkaListener(topics = "user-registration", groupId = "statistics-group")
    public void listenUserRegistration(UserRegistrationEvent event){
        userRegistrationEventRepository.save(event);
    }

    /**
     * Сохраняет в БД информации о созданной брони
     * @param event Объект информации о создании брони
     */
    @KafkaListener(topics = "room-booking", groupId = "statistics-group")
    public void listenBooking(RoomBookingEvent event){

        roomBookingEventRepository.save(event);
    }

}
