package com.learn.ms.service.Impl;

import com.learn.ms.dto.UserDto;
import com.learn.ms.entity.User;
import com.learn.ms.exception.ResourceNotFoundException;
import com.learn.ms.repository.UserRepository;
import com.learn.ms.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDto create(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        String gender = String.valueOf(userDto.getGender());
        if ("MALE".equalsIgnoreCase(gender)) {
            user.setGender("MALE");
        } else if ("FEMALE".equalsIgnoreCase(gender)) {
            user.setGender("FEMALE");
        } else {
            user.setGender("OTHER");
        }
        user.setUuid(UUID.randomUUID());
        user.setJoinDate(LocalDate.now());
        User user1 = userRepository.save(user);
        BeanUtils.copyProperties(user1, userDto);
        return userDto;
    }

    @Cacheable(value = "user", key = "#uuid")
    @Override
    public UserDto get(UUID uuid) {
        User user = userRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("user not found with is ID : " + uuid + " !!"));
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            return userDto;
        }).toList();
    }

    @CachePut(value = "user", key = "#user.uuid")
    @Override
    public UserDto update(UUID uuid, UserDto userDto) {
        User user = userRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("user not found with is ID : " + uuid + " !!"));

        BeanUtils.copyProperties(userDto, user);
        String gender = String.valueOf(userDto.getGender());
        if ("MALE".equalsIgnoreCase(gender)) {
            user.setGender("MALE");
        } else if ("FEMALE".equalsIgnoreCase(gender)) {
            user.setGender("FEMALE");
        } else {
            user.setGender("OTHER");
        }
        user.setUuid(UUID.randomUUID());
        user.setJoinDate(LocalDate.now());
        User user1 = userRepository.save(user);
        BeanUtils.copyProperties(user1, userDto);
        return userDto;
    }

    @CacheEvict(value = "user", key = "#uuid")
    @Override
    public void delete(UUID uuid) {
        User user = userRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("user not found with is ID : " + uuid + " !!"));
        userRepository.delete(user);
    }
}
