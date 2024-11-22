package com.example.hotel_app.controllers;

import com.example.hotel_app.DTO.HotelDTOForReading;
import com.example.hotel_app.DTO.HotelDTOForWriting;
import com.example.hotel_app.DTO.DtoForPagination;
import com.example.hotel_app.services.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController implements HotelAbstractController {

    private final HotelService hotelService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Override
    public ResponseEntity<HotelDTOForReading> findById(@PathVariable long id) {
        HotelDTOForReading hotel = hotelService.findByID(id);
        return hotel == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(hotel);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<HotelDTOForReading> makeNewHotel(@RequestBody HotelDTOForWriting newHotel) {
        HotelDTOForReading hotel = hotelService.makeOne(newHotel);
        return hotel == null ? ResponseEntity.badRequest().build() :
                ResponseEntity.status(HttpStatus.CREATED).body(hotel);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<HotelDTOForReading> editHotel(@RequestBody HotelDTOForWriting hotelForEditing) {
        HotelDTOForReading hotel = hotelService.editOne(hotelForEditing);
        return hotel == null ? ResponseEntity.notFound().build() :
                ResponseEntity.ok(hotel);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<String> deleteHotel(@PathVariable long id) {
        Boolean ans = hotelService.deleteOne(id);
        return ans ? ResponseEntity.ok("Отель с id: " + id + " успешно удален") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Отель с id: " + id + " не найден");
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Override
    public ResponseEntity<List<HotelDTOForReading>> getAllHotels() {
        return ResponseEntity.ok(hotelService.getList());
    }

    @PostMapping("/mark")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Override
    public ResponseEntity<String> changeHotelRating(@RequestParam Long hotelId, @RequestParam Integer newMark) {
        return ResponseEntity.ok(hotelService.changeHotelRating(hotelId, newMark));
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Override
    public ResponseEntity<DtoForPagination> getFilteredHotels(@RequestParam(required = false) Long id, @RequestParam(required = false) String name,
                                                              @RequestParam(required = false) String title, @RequestParam(required = false) String city,
                                                              @RequestParam(required = false) String address, @RequestParam(required = false) Double distanceFromCityCenterInMetres,
                                                              @RequestParam(required = false) Double rating, @RequestParam(required = false) Long quantityOfVices,
                                                              @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok((hotelService.getSpecificHotels(id, name,
                title, city,
                address, distanceFromCityCenterInMetres,
                rating, quantityOfVices,
                page, size)));

    }
}
