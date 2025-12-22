package com.ujjwal.ecommerce.service;

import com.ujjwal.ecommerce.exceptions.UserException;
import com.ujjwal.ecommerce.payload.dto.UserDto;
import com.ujjwal.ecommerce.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse signup(UserDto userDto) throws UserException;
    AuthResponse login(UserDto userDto) throws UserException;
}
