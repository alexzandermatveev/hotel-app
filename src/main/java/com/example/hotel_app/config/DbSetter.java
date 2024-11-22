package com.example.hotel_app.config;

import com.example.hotel_app.model.*;
import com.example.hotel_app.repository.BookingRepository;
import com.example.hotel_app.repository.HotelRepository;
import com.example.hotel_app.repository.RoomRepository;
import com.example.hotel_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;


/**
 * Очищает базу данных и создает сущности по-умолчанию
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "create-default-entities", havingValue = "true")
public class DbSetter implements CommandLineRunner {


    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();
        hotelRepository.deleteAll();
        roomRepository.deleteAll();
        bookingRepository.deleteAll();
        // дефолтные сущности
        createDefaultUsers();
    }

    @Transactional
    private void createDefaultUsers() {
        AppUser admin = new AppUser();
        admin.setRole(UserRoles.ROLE_ADMIN);
        admin.setEmail("admin@example.com");
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));

        AppUser user = new AppUser();
        user.setRole(UserRoles.ROLE_USER);
        user.setEmail("user@example.com");
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));

        admin = userRepository.save(admin);
        userRepository.save(user);

        Hotel hotel = new Hotel();
        hotel.setCity("Санкт-Петербург");
        hotel.setAddress("Вознесенский пр., 1");
        hotel.setRating(4.0);
        hotel.setName("Four Seasons Hotel");
        hotel.setDistanceFromCityCenterInMetres(100);
        hotel.setQuantityOfVoices(10);

        hotel = hotelRepository.save(hotel);

        Room room1 = new Room();
        room1.setName("room1");
        room1.setHotel(hotel);
        room1.setDescription("example room");
        room1.setMaxPeople(2);
        room1.setPrice(1000.0);
        room1.setRoomNumber("001");

        Room room2 = new Room();
        room2.setName("room2");
        room2.setHotel(hotel);
        room2.setDescription("example room");
        room2.setMaxPeople(1);
        room2.setPrice(800.0);
        room2.setRoomNumber("002");

        room1 = roomRepository.save(room1);
        room2 = roomRepository.save(room2);

        Booking booking1 = new Booking();
        booking1.setUser(admin);
        booking1.setRoom(room1);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.DECEMBER, 30);
        booking1.setEnterDate(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        booking1.setExitDate(calendar.getTime());

        Booking booking2 = new Booking();
        booking2.setUser(user);
        booking2.setRoom(room2);
        calendar.set(2024, Calendar.DECEMBER, 31);
        booking2.setEnterDate(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        booking2.setExitDate(calendar.getTime());

        bookingRepository.save(booking1);
        bookingRepository.save(booking2);
    }
}

