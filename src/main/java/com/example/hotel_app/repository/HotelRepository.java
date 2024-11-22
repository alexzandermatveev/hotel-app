package com.example.hotel_app.repository;

import com.example.hotel_app.model.Hotel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий по работе с сущностями {@link Hotel}
 */
@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {
    Boolean existsByAddress(String address);

    boolean existsById(Long id);

    Hotel findByName(String name);

    boolean existsByName(String name);

    class HotelsSpecification {

        public static Specification<Hotel> byId(Long id) {
            return (root, query, criteriaBuilder) ->
                    id == null ? null : criteriaBuilder.equal(root.get("id"), id);
        }

        public static Specification<Hotel> byName(String name) {
            return (root, query, criteriaBuilder) ->
                    name == null ? null : criteriaBuilder.equal(root.get("name"), name);
        }

        public static Specification<Hotel> byCity(String city) {
            return (root, query, criteriaBuilder) ->
                    city == null ? null : criteriaBuilder.equal(root.get("city"), city);
        }

        public static Specification<Hotel> byTitle(String title) {
            return (root, query, criteriaBuilder) ->
                    title == null ? null : criteriaBuilder.equal(root.get("titleOfAdvertisement"), title);
        }

        public static Specification<Hotel> byAddress(String address) {
            return (root, query, criteriaBuilder) ->
                    address == null ? null : criteriaBuilder.equal(root.get("address"), address);
        }

        public static Specification<Hotel> byMinRating(Double minRating) {
            return (root, query, criteriaBuilder) ->
                    minRating == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), minRating);
        }

        public static Specification<Hotel> byMinQuantityOfVoices(Long quantityOfVoices) {
            return (root, query, criteriaBuilder) ->
                    quantityOfVoices == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("quantityOfVoices"), quantityOfVoices);
        }

        public static Specification<Hotel> byMaxDistanceFromCityCenterInMetres(Double distanceFromCityCenterInMetres) {
            return (root, query, criteriaBuilder) ->
                    distanceFromCityCenterInMetres == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("distanceFromCityCenterInMetres"), distanceFromCityCenterInMetres);
        }

    }

}
