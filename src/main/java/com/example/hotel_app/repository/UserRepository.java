package com.example.hotel_app.repository;

import com.example.hotel_app.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий по работе с сущностями {@link AppUser}
 */
@Repository
public interface UserRepository extends JpaRepository<AppUser, String> {
    Boolean existsByUsername(String username);

    AppUser findByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUsernameAndEmail(String username, String email);

    void deleteByUsername(String username);
}
