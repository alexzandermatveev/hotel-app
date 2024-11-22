package com.example.hotel_app.repository;

import com.example.hotel_app.model.Booking;
import com.example.hotel_app.model.Room;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Репозиторий по работе с сущностями {@link Room}
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {
    boolean existsById(long id);

    Room findById(long id);

    /**
     * Метод с жадной загрузкой {@link Room#occupatedDates}
     *
     * @param id
     * @return {@link Room}
     */
    @EntityGraph(attributePaths = "occupatedDates")
    Room findWithOccupatedDatesById(Long id);

    class RoomSpecification {

        public static Specification<Room> byId(Long id) {
            return (root, query, criteriaBuilder) ->
                    id == null ? null : criteriaBuilder.equal(root.get("id"), id);
        }

        public static Specification<Room> byDescription(String description) {
            return (root, query, criteriaBuilder) ->
                    description == null ? null : criteriaBuilder.like(root.get("description"), description);
        }

        public static Specification<Room> byMinPrice(Double minPrice) {
            return (root, query, criteriaBuilder) ->
                    minPrice == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
        }

        public static Specification<Room> byMaxPrice(Double maxPrice) {
            return (root, query, criteriaBuilder) ->
                    maxPrice == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
        }

        public static Specification<Room> byHotelId(Long id) {
            return (root, query, criteriaBuilder) ->
                    id == null ? null : criteriaBuilder.equal(root.get("hotel").get("id"), id);
        }

        public static Specification<Room> byMaxGuests(Integer maxPeople) {
            return (root, query, criteriaBuilder) ->
                    maxPeople == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("maxPeople"), maxPeople);
        }

        public static Specification<Room> byDates(Date enterDate, Date exitDate) {
            return (root, query, criteriaBuilder) ->
            {
                if (enterDate != null && exitDate != null && exitDate.after(enterDate)) {
                    Join<Room, Booking> bookings = root.join("bookings");

                    return criteriaBuilder.not(
                            criteriaBuilder.and(
                                    criteriaBuilder.lessThan(bookings.get("enterDate"), exitDate),
                                    criteriaBuilder.greaterThan(bookings.get("exitDate"), enterDate)
                            )
                    );
                }
                return null;
            };
        }

    }
}
