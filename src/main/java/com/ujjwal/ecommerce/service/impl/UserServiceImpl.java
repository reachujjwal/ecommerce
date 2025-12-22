package com.ujjwal.ecommerce.service.impl;

import com.ujjwal.ecommerce.configuration.JwtProvider;
import com.ujjwal.ecommerce.exceptions.UserException;
import com.ujjwal.ecommerce.model.User;
import com.ujjwal.ecommerce.repository.UserRepository;
import com.ujjwal.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public User getUserFromJwtToken(String token) throws UserException {
        String email = jwtProvider.getEmailFromToken(token);
        User user = userRepository.findByEmail(email);
        if(user==null){
            throw new UserException("Invalid Token");
        }
        return user;
    }

    @Override
    public User getCurrentUser() throws UserException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        if(user==null){
            throw new UserException("User not found");
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) throws UserException {
        User user = userRepository.findByEmail(email);
        if(user==null){
            throw new UserException("User not found");
        }
        return user;
    }

    @Override
    public User getUserById(Long id) {
        User user = userRepository.findById(id);
        if(user==null){
            return null;
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
