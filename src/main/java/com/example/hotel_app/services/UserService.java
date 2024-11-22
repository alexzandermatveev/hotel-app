package com.example.hotel_app.services;

import com.example.hotel_app.DTO.UserDtoReading;
import com.example.hotel_app.DTO.UserDtoWriting;
import com.example.hotel_app.DTOconverter.UserMapper;
import com.example.hotel_app.exceptions.UserNotFound;
import com.example.hotel_app.model.AppUser;
import com.example.hotel_app.model.UserRegistrationEvent;
import com.example.hotel_app.model.UserRoles;
import com.example.hotel_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements AbstractUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public UserDtoReading findById(String username) {
        if (userRepository.existsByUsername(username)) {
            return userMapper.userToReader(userRepository.findByUsername(username));
        }
        throw new UserNotFound("user with username: " + username + "not found");
    }

    @Override
    public UserDtoReading createUser(UserDtoWriting instance, String userRole) {
        AppUser potentialUser = userMapper.writerToUser(instance, passwordEncoder);
        userRole = userRole.toUpperCase();
        userRole = !userRole.contains("ROLE_") ? "ROLE_".concat(userRole) :
                userRole;
        try{
            UserRoles role = UserRoles.valueOf(userRole);
            potentialUser.setRole(role);
        }catch (IllegalArgumentException exception){
            throw new IllegalArgumentException("the role: " + userRole + "not available");
        }

        if (!userRepository.existsByUsername(potentialUser.getUsername()) &&
                !userRepository.existsByEmail(potentialUser.getEmail())) {

            UserRegistrationEvent userRegistrationEvent = new UserRegistrationEvent(null,
                    potentialUser.getUsername(),
                    Date.from(Instant.now()));
            kafkaTemplate.send("user-registration", userRegistrationEvent);
            return userMapper.userToReader(userRepository.save(potentialUser));
        }
        throw new IllegalArgumentException("cant apply this instance for creating a new user");
    }

    @Override
    public UserDtoReading editUser(UserDtoWriting instance, String userRole) {

        AppUser user = userMapper.writerToUser(instance, passwordEncoder);

        try{
            UserRoles role = UserRoles.valueOf(userRole);
            user.setRole(role);
        }catch (IllegalArgumentException exception){
            throw new IllegalArgumentException("the role: " + userRole + "not available");
        }

        if(userRepository.existsByUsername(user.getUsername())){
            return userMapper.userToReader(userRepository.save(user));
        }
        throw new UserNotFound("user with username: " + user.getUsername() + "not found");
    }

    @Override
    @Transactional
    public Boolean deleteUser(String username) {
        if(userRepository.existsByUsername(username)){
            userRepository.deleteByUsername(username);
            return true;
        }
        throw new UserNotFound("user with username: " + username + "not found");
    }

    @Override
    public List<UserDtoReading> getList() {
        return userRepository.findAll().stream()
                .map(userMapper::userToReader)
                .toList();
    }
}
