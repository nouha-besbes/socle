package com.socle.service;

import java.util.List;
import java.util.Optional;

import com.socle.service.dto.UserDto;
import com.socle.service.exception.ResourceNotFoundException;

public interface IUserService {

    List<UserDto> findAll();

    Optional<UserDto> findDtoById(Long userId) throws ResourceNotFoundException;

    UserDto save(UserDto userDto);

    UserDto update(Long userId, UserDto userDto) throws ResourceNotFoundException;

    void deleteById(Long userId) throws ResourceNotFoundException;

}
