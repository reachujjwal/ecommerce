package com.ujjwal.ecommerce.payload.response;

import com.ujjwal.ecommerce.payload.dto.UserDto;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;

    private UserDto user;


}
