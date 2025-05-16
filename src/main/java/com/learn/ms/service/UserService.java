package com.learn.ms.service;

import com.learn.ms.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDto create(UserDto userDto);

    UserDto get(UUID uuid);

    List<UserDto> getAll();

    UserDto update(UUID uuid, UserDto userDto);

    void delete(UUID uuid);


}
