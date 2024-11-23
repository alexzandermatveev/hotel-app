package com.example.hotel_app.controllers;

import com.example.hotel_app.dto.BookingDtoReading;
import com.example.hotel_app.dto.BookingDtoWriter;
import com.example.hotel_app.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController implements BookingAbstractController{

    private final BookingService bookingService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Override
    public ResponseEntity<BookingDtoReading> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Override
    public ResponseEntity<BookingDtoReading> makeNewBooking(@RequestBody BookingDtoWriter newBooking) {
        return ResponseEntity.ok(bookingService.bookRoom(newBooking));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Override
    public ResponseEntity<BookingDtoReading> editBooking(@RequestBody BookingDtoWriter bookingForEditing,@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.editBooking(bookingForEditing, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Override
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
        if(bookingService.deleteBooking(id)){
            return ResponseEntity.ok("Booking с id: " + id + " успешно удален");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking с id: " + id + " не найден");
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<List<BookingDtoReading>> getAllBooking() {
        return ResponseEntity.ok(bookingService.findAll());
    }
}
