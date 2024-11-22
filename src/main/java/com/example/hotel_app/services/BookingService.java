package com.example.hotel_app.services;

import com.example.hotel_app.DTO.BookingDtoReading;
import com.example.hotel_app.DTO.BookingDtoWriter;
import com.example.hotel_app.DTOconverter.BookingMapper;
import com.example.hotel_app.exceptions.BookingNotFound;
import com.example.hotel_app.model.Booking;
import com.example.hotel_app.model.Room;
import com.example.hotel_app.model.RoomBookingEvent;
import com.example.hotel_app.repository.BookingRepository;
import com.example.hotel_app.repository.RoomRepository;
import com.example.hotel_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BookingService implements AbstractBookingService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public List<BookingDtoReading> findAll() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::bookingToReader)
                .toList();
    }

    @Override
    public BookingDtoReading bookRoom(BookingDtoWriter writer) {

        Booking booking = bookingMapper.writerToBooking(writer,
                userRepository,
                roomRepository);

        checkingDates(booking);

        Booking saved = bookingRepository.save(booking);
        RoomBookingEvent roomBookingEvent = new RoomBookingEvent(null,
                saved.getUser().getUsername(),
                saved.getRoom().getId(),
                saved.getEnterDate(),
                saved.getExitDate(),
                Date.from(Instant.now()));
        kafkaTemplate.send("room-booking", roomBookingEvent);

        return bookingMapper.bookingToReader(saved);
    }

    /**
     * Проверяет возможность добавления указанных дат для резервирования комнаты
     * @param booking Экземпляр брони {@link Booking}
     */
    private void checkingDates(Booking booking) {
        Room room = roomRepository.findWithOccupatedDatesById(booking.getRoom().getId());
        Date start = booking.getEnterDate();
        Date finish = booking.getExitDate();
        ArrayList<Date> occupied =  new ArrayList<>(room.getOccupatedDates());

        boolean containsIntersectedDates = false;
        for (Date date : occupied) {
            if (!(finish.before(date) || start.after(date))) {
                containsIntersectedDates = true;
                break;
            }
        }
        if (containsIntersectedDates) {
            throw new IllegalArgumentException("cant apply this instance for creating a new room, because\n" +
                    "booking contains dates that were occupied before");
        }

        List<Date> newOccupied = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        newOccupied.add(start);
        while (calendar.getTime().compareTo(finish) < 0) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            newOccupied.add(calendar.getTime());
        }
        occupied.addAll(newOccupied);
        room.setOccupatedDates(occupied);
        roomRepository.save(room);
    }

    /**
     * Удаляет у сущности {@link Room} зарезервированные даты
     * @param oldBooking Старая версия брони {@link Booking}
     */
    private void clearOldBookingDates(Booking oldBooking) {
        Room room = roomRepository.findWithOccupatedDatesById(oldBooking.getRoom().getId());
        final Date start = oldBooking.getEnterDate();
        final Date finish = oldBooking.getExitDate();

        List<Date> oldDatesWithoutBookingDates = room.getOccupatedDates().stream()
                .filter(date -> date.before(start) || date.after(finish))
                .toList();

        room.setOccupatedDates(oldDatesWithoutBookingDates);
        roomRepository.save(room);

    }

    @Override
    public BookingDtoReading editBooking(BookingDtoWriter writer, Long id) {

        if (bookingRepository.existsById(id)) {
            Booking oldBooking = bookingRepository.findById(id).get();
            Booking newBooking = bookingMapper.writerToBooking(writer,
                    userRepository,
                    roomRepository);

            newBooking.setId(id);

            clearOldBookingDates(oldBooking);
            checkingDates(newBooking);

            return bookingMapper.bookingToReader(bookingRepository.save(newBooking));
        }
        throw new BookingNotFound("no booking with id:" + id + "in DB");
    }

    @Override
    public boolean deleteBooking(Long id) {
        if (bookingRepository.existsById(id)) {
            Booking oldBooking = bookingRepository.findById(id).get();
            clearOldBookingDates(oldBooking);
            bookingRepository.deleteById(id);
            return true;
        }
        throw new BookingNotFound("no booking with id:" + id + "in DB");
    }

    @Override
    public BookingDtoReading findById(Long id) {
        if (bookingRepository.existsById(id)) {
            return bookingMapper.bookingToReader(bookingRepository.findById(id).get());
        }
        throw new BookingNotFound("no booking with id:" + id + "in DB");
    }
}
