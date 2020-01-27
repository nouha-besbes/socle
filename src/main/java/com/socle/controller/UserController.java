package com.socle.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.socle.service.IUserService;
import com.socle.service.dto.UserDto;
import com.socle.service.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUsersById(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok().body(userService.findDtoById(userId).get());
    }

    @PostMapping("/users")
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        return userService.save(userDto);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(value = "id") Long userId,
            @Valid @RequestBody UserDto userDetails) throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.update(userId, userDetails));
    }

    @DeleteMapping("/user/{id}")
    public @ResponseBody Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws Exception {
        userService.deleteById(userId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
