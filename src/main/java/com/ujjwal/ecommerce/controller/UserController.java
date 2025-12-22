package com.ujjwal.ecommerce.controller;

import com.ujjwal.ecommerce.exceptions.UserException;
import com.ujjwal.ecommerce.mapper.UserMapper;
import com.ujjwal.ecommerce.model.User;
import com.ujjwal.ecommerce.payload.dto.UserDto;
import com.ujjwal.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(
            @RequestHeader("Authorization") String jwt
    ) throws UserException {
        User user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable long id
    ) throws UserException {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new UserException("User not found");
        }
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }
}
