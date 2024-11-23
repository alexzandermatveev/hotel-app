package com.example.hotel_app.controllers;

import com.example.hotel_app.dto.DtoForPagination;
import com.example.hotel_app.dto.RoomDtoReading;
import com.example.hotel_app.dto.RoomDtoWriting;
import com.example.hotel_app.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController implements RoomAbstractController {

    private final RoomService roomService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Override
    public ResponseEntity<RoomDtoReading> findById(@PathVariable long id) {
        return ResponseEntity.ok(roomService.findByID(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<RoomDtoReading> makeNewRoom(@RequestBody RoomDtoWriting newRoom) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.makeOne(newRoom));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<RoomDtoReading> editRoom(@RequestBody RoomDtoWriting roomForEditing) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(roomService.editOne(roomForEditing));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<String> deleteRoom(@PathVariable long id) {
        if (roomService.deleteOne(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Комната с id: " + id + " успешно удалена");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Комната с id: " + id + " не найдена");
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Override
    public ResponseEntity<List<RoomDtoReading>> getAllRooms() {
        return ResponseEntity.ok(roomService.getList());
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Override
    public ResponseEntity<DtoForPagination<RoomDtoReading>> getFilteredRooms(@RequestParam(required = false) Long id, @RequestParam(required = false) String description, @RequestParam(required = false) Double minPrice,
                                                                             @RequestParam(required = false) Double maxPrice, @RequestParam(required = false) Long hotelId,
                                                                             @RequestParam(required = false) Integer maxGuests, @RequestParam(required = false) Date enterDate,
                                                                             @RequestParam(required = false) Date exitDate, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(roomService.getFilteredRooms(id, description, minPrice,
                maxPrice, hotelId,
                maxGuests, enterDate, exitDate,
                page, size));
    }
}
