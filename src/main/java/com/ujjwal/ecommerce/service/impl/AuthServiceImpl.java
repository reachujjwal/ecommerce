package com.ujjwal.ecommerce.service.impl;

import com.ujjwal.ecommerce.repository.UserRepository;
import com.ujjwal.ecommerce.configuration.JwtProvider;
import com.ujjwal.ecommerce.domain.UserRole;
import com.ujjwal.ecommerce.exceptions.UserException;
import com.ujjwal.ecommerce.mapper.UserMapper;
import com.ujjwal.ecommerce.model.User;
import com.ujjwal.ecommerce.payload.dto.UserDto;
import com.ujjwal.ecommerce.payload.response.AuthResponse;
import com.ujjwal.ecommerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserImplementation customUserImplementation;


    @Override
    public AuthResponse signup(UserDto userDto) throws UserException {
        User user = userRepository.findByEmail(userDto.getEmail());
        if(user!=null){
            throw new UserException("Email id is already registered.!");
        }

        if(userDto.getRole().equals(UserRole.ROLE_ADMIN)){
            throw new UserException("Role admin is not allowed.!");
        }

        User newUser = new User();
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setRole(userDto.getRole());
        newUser.setFullName(userDto.getFullName());
        newUser.setPhone(userDto.getPhone());
        newUser.setLastLoginAt(LocalDateTime.now());
        newUser.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(newUser);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Registered Successfully!");
        authResponse.setUser(UserMapper.toDTO(savedUser));

        return authResponse;
    }

    @Override
    public AuthResponse login(UserDto userDto) throws UserException {
        String email = userDto.getEmail();
        String password = userDto.getPassword();

        Authentication authentication = authenticate(email,password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles = authorities.iterator().next().getAuthority();
        String jwt = jwtProvider.generateToken(authentication);

        User user =   userRepository.findByEmail(email);
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login Successfully!");
        authResponse.setUser(UserMapper.toDTO(user));

        return authResponse;
    }

    private Authentication authenticate(String email, String password) throws UserException {
        UserDetails userDetails = customUserImplementation.loadUserByUsername(email);
        if(userDetails == null){
            throw new UserException("User does not registered with this email" +email);
        }

        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new UserException("Password Does not match");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }
}
