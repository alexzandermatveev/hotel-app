package com.example.hotel_app.services;

import com.example.hotel_app.dto.DtoForPagination;
import com.example.hotel_app.dto.RoomDtoReading;
import com.example.hotel_app.dto.RoomDtoWriting;
import com.example.hotel_app.dtoConverter.RoomMapper;
import com.example.hotel_app.exceptions.RoomNotFound;
import com.example.hotel_app.model.Room;
import com.example.hotel_app.repository.HotelRepository;
import com.example.hotel_app.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService implements AbstractRoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomMapper roomMapper;

    @Override
    public RoomDtoReading findByID(long id) {
        if (roomRepository.existsById(id)) {
            return roomMapper.roomToReader(roomRepository.findById(id));
        }
        throw new RoomNotFound("no room with id: " + id + " in DB");
    }

    @Override
    public RoomDtoReading makeOne(RoomDtoWriting instance) {
        if (instance != null) {
            Room newRoom = roomMapper.writerToRoom(instance, hotelRepository);
            newRoom.setId(null);
            return roomMapper.roomToReader(roomRepository.save(newRoom));
        }
        throw new IllegalArgumentException("cant apply this instance for creating a new room");
    }

    @Override
    public RoomDtoReading editOne(RoomDtoWriting instance) {
        if (roomRepository.existsById(instance.getId())) {
            Room editedRoom = roomMapper.writerToRoom(instance, hotelRepository);
            return roomMapper.roomToReader(roomRepository.save(editedRoom));
        }
        throw new RoomNotFound("no room with id:" + instance.getId() + "in DB");
    }

    @Override
    public Boolean deleteOne(long id) {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
            return true;
        }
        throw new RoomNotFound("no room with id:" + id + "in DB");
    }

    @Override
    public List<RoomDtoReading> getList() {
        return roomRepository.findAll().stream()
                .map(roomMapper::roomToReader)
                .toList();
    }

    @Override
    public DtoForPagination<RoomDtoReading> getFilteredRooms(Long id, String description, Double minPrice,
                                                             Double maxPrice, Long hotelId,
                                                             Integer maxGuests, Date enterDate, Date exitDate,
                                                             Integer page, Integer size) {
        Specification<Room> specification = Specification
                .where(RoomRepository.RoomSpecification.byId(id))
                .and(RoomRepository.RoomSpecification.byDescription(description))
                .and(RoomRepository.RoomSpecification.byMinPrice(minPrice))
                .and(RoomRepository.RoomSpecification.byMaxPrice(maxPrice))
                .and(RoomRepository.RoomSpecification.byHotelId(hotelId))
                .and(RoomRepository.RoomSpecification.byMaxGuests(maxGuests))
                .and(RoomRepository.RoomSpecification.byDates(enterDate, exitDate));

        Page<Room> rooms = roomRepository.findAll(specification, PageRequest.of(page, size));
        DtoForPagination<RoomDtoReading> representation = new DtoForPagination<>();
        representation.setTotalFounded(rooms.getTotalElements());
        representation.setNumberOfPage(rooms.getNumber());
        representation.setTotalNumberOfPages(rooms.getTotalPages());
        representation.setTotalQuantityInDb(roomRepository.count());
        representation.setTotalQuantityOnCurrentPage(rooms.getTotalElements());
        representation.setObjects(rooms.map(roomMapper::roomToReader).getContent());

        return representation;
    }
}
