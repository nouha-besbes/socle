package com.socle.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.socle.repository.IUserRepository;
import com.socle.repository.model.User;
import com.socle.service.IUserService;
import com.socle.service.dto.UserDto;
import com.socle.service.exception.ResourceNotFoundException;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public UserServiceImpl(IUserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Cacheable("users")
    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> findDtoById(Long userId) throws ResourceNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new ResourceNotFoundException("User not found " + userId);
        }
        return Optional.of(modelMapper.map(user.get(), UserDto.class));
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public UserDto save(UserDto userDto) {
        return modelMapper.map(userRepository.save(modelMapper.map(userDto, User.class)), UserDto.class);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public UserDto update(Long userId, UserDto userDto) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setId(userId);
        userRepository.save(user);
        userDto.setId(userId);
        return userDto;
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public void deleteById(Long userId) throws ResourceNotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found on :: " + userId);
        }
        userRepository.deleteById(userId);
    }

}
