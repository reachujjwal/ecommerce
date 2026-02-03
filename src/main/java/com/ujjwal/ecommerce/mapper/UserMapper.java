package com.ujjwal.ecommerce.mapper;

import com.ujjwal.ecommerce.model.User;
import com.ujjwal.ecommerce.payload.dto.UserDto;

import java.time.LocalDateTime;

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
        userDto.setBranchId(savedUser.getBranch()!=null ? savedUser.getBranch().getId():null);
        userDto.setStoreId(savedUser.getStore()!=null ? savedUser.getStore().getId() :null);
        return userDto;
    }

    public static User toEntity(UserDto userDto) {
        User createdUser = new User();
        createdUser.setEmail(userDto.getEmail());
        createdUser.setPassword(userDto.getPassword());
        createdUser.setRole(userDto.getRole());
        createdUser.setCreatedAt(LocalDateTime.now());
        createdUser.setUpdatedAt(userDto.getUpdatedAt());
        createdUser.setLastLoginAt(userDto.getLastLoginAt());
        createdUser.setPhone(userDto.getPhone());
        createdUser.setFullName(userDto.getFullName());
        return createdUser;
    }
}
