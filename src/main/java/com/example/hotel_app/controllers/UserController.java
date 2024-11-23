package com.example.hotel_app.controllers;

import com.example.hotel_app.dto.UserDtoReading;
import com.example.hotel_app.dto.UserDtoWriting;
import com.example.hotel_app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements UserAbstractController{

    private final UserService userService;

    @GetMapping("/{username}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Override
    public ResponseEntity<UserDtoReading> findById(@PathVariable String username) {
        return ResponseEntity.ok(userService.findById(username));
    }

    @PostMapping
    @Override
    public ResponseEntity<UserDtoReading> createNewUser(@RequestBody UserDtoWriting newUser, @RequestParam String userRole) {
        return ResponseEntity.ok(userService.createUser(newUser, userRole));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Override
    public ResponseEntity<UserDtoReading> editUser(@RequestBody UserDtoWriting userForEditing,@RequestParam String userRole) {
        return ResponseEntity.ok(userService.editUser(userForEditing, userRole));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Override
    public ResponseEntity<String> deleteUser(@RequestParam String username) {
        if(userService.deleteUser(username)){
            return ResponseEntity.ok("User с username: " + username + " успешно удален");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User с username: " + username + " не найден");
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Override
    public ResponseEntity<List<UserDtoReading>> getAllUsers() {
        return ResponseEntity.ok(userService.getList());
    }
}
