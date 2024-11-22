package com.example.hotel_app.services;

import com.example.hotel_app.DTO.HotelDTOForReading;
import com.example.hotel_app.DTO.HotelDTOForWriting;
import com.example.hotel_app.DTO.DtoForPagination;
import com.example.hotel_app.DTOconverter.HotelMapper;
import com.example.hotel_app.DTOconverter.RoomMapper;
import com.example.hotel_app.exceptions.HotelNotFound;
import com.example.hotel_app.model.Hotel;
import com.example.hotel_app.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService implements AbstractHotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final RoomMapper roomMapper;


    @Override
    public HotelDTOForReading findByID(long id) {
        if (hotelRepository.existsById(id)) {
            return hotelMapper.hotelToReader(hotelRepository.findById(id).get(), roomMapper);
        }
        throw new HotelNotFound("no hotel with id: " + id + " in DB");
    }

    @Override
    public HotelDTOForReading makeOne(HotelDTOForWriting writer) {
        if (writer != null && !hotelRepository.existsByAddress(writer.getAddress())) {
            Hotel newHotel = hotelMapper.writerToHotel(writer);
            newHotel.setId(null);
            hotelRepository.save(newHotel);
            return hotelMapper.hotelToReader(hotelRepository.save(newHotel), roomMapper);
        }
        throw new IllegalArgumentException("cant apply this instance for creating a new hotel");
    }

    @Override
    public HotelDTOForReading editOne(HotelDTOForWriting writer) {
        if (hotelRepository.existsById(writer.getId())) {
            Hotel hotelNew = hotelMapper.writerToHotel(writer);
            return hotelMapper.hotelToReader(hotelRepository.save(hotelNew), roomMapper);
        }
        throw new HotelNotFound("no hotel with id: " + writer.getId() + " in DB");
    }

    @Override
    public Boolean deleteOne(long id) {
        if (hotelRepository.existsById(id)) {
            hotelRepository.deleteById(id);
            return true;
        }
        throw new HotelNotFound("no hotel with id: " + id + " in DB");
    }

    @Override
    public List<HotelDTOForReading> getList() {
        return hotelRepository.findAll().stream()
                .map(hotel -> hotelMapper.hotelToReader(hotel, roomMapper))
                .toList();
    }

    @Override
    public String changeHotelRating(Long hotelId, Integer newMark) {

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFound("no hotel with id: " + hotelId + " in DB"));

        double rating = hotel.getRating();
        long numberOfRating = hotel.getQuantityOfVoices();
        double totalRating = rating * numberOfRating;
        totalRating = totalRating - rating + newMark;
        rating = (double) Math.round((totalRating / numberOfRating * 10)) / 10;

        hotel.setRating(rating);
        hotel.setQuantityOfVoices(numberOfRating + 1);
        hotelRepository.save(hotel);
        return "you successfully rated hotel " + hotel.getName() + " with " + newMark +
                "\n now its rating is: " + rating +
                "\n based on: " + hotel.getQuantityOfVoices() + " voices";
    }

    @Override
    public DtoForPagination<HotelDTOForReading> getSpecificHotels(Long id, String name, String title,
                                              String city, String address,
                                              Double distanceFromCityCenterInMetres, Double rating, Long quantityOfVices,
                                              Integer page, Integer size) {
        Specification<Hotel> specification = Specification
                .where(HotelRepository.HotelsSpecification.byId(id))
                .and(HotelRepository.HotelsSpecification.byName(name))
                .and(HotelRepository.HotelsSpecification.byTitle(title))
                .and(HotelRepository.HotelsSpecification.byCity(city))
                .and(HotelRepository.HotelsSpecification.byAddress(address))
                .and(HotelRepository.HotelsSpecification.byMaxDistanceFromCityCenterInMetres(distanceFromCityCenterInMetres))
                .and(HotelRepository.HotelsSpecification.byMinRating(rating))
                .and(HotelRepository.HotelsSpecification.byMinQuantityOfVoices(quantityOfVices));

        Page<Hotel> hotelsPage = hotelRepository.findAll(specification, PageRequest.of(page, size));
        DtoForPagination<HotelDTOForReading> representedPage = new DtoForPagination<>();

        representedPage.setNumberOfPage(hotelsPage.getNumber());
        representedPage.setTotalNumberOfPages(hotelsPage.getTotalPages());
        representedPage.setTotalQuantityInDb(hotelRepository.count());
        representedPage.setTotalFounded(hotelsPage.getTotalElements());
        representedPage.setTotalQuantityOnCurrentPage(size.longValue());
        representedPage.setObjects(hotelsPage.getContent().stream()
                .map(hotel -> hotelMapper.hotelToReader(hotel, roomMapper))
                .toList());


        return representedPage;
    }
}
