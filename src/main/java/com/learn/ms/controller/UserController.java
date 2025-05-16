package com.learn.ms.controller;


import com.learn.ms.dto.UserDto;
import com.learn.ms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.create(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID uuid) {
        UserDto userDto = userService.get(uuid);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<UserDto> updateUser(@PathVariable UUID uuid, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.update(uuid, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID uuid) {
        userService.delete(uuid);
        return ResponseEntity.noContent().build();
    }

}
