package com.ujjwal.ecommerce.mapper;

import com.ujjwal.ecommerce.model.User;
import com.ujjwal.ecommerce.payload.dto.UserDto;

public class UserMapper {
    public static UserDto toDTO(User savedUser) {
        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        userDto.setEmail(savedUser.getEmail());
        userDto.setRole(savedUser.getRole());
        //userDto.setPassword(savedUser.getPassword());
        userDto.setCreatedAt(savedUser.getCreatedAt());
        userDto.setUpdatedAt(savedUser.getUpdatedAt());
        userDto.setLastLoginAt(savedUser.getLastLoginAt());
        userDto.setPhone(savedUser.getPhone());
        userDto.setFullName(savedUser.getFullName());
        return userDto;
    }
}
