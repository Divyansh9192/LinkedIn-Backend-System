package com.divyansh.linkedin.user_service.service;

import com.divyansh.linkedin.user_service.dto.LoginRequestDTO;
import com.divyansh.linkedin.user_service.dto.SignUpRequestDTO;
import com.divyansh.linkedin.user_service.dto.UserDTO;
import com.divyansh.linkedin.user_service.entity.User;
import com.divyansh.linkedin.user_service.repository.UserRepository;
import com.divyansh.linkedin.user_service.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JWTService jwtService;

    public UserDTO signUp(SignUpRequestDTO signUpRequestDTO) throws BadRequestException {
        boolean exists = userRepository.existsByEmail(signUpRequestDTO.getEmail());

        if (exists) throw new BadRequestException("User already exists");

        User user = modelMapper.map(signUpRequestDTO, User.class);
        user.setPassword(PasswordUtil.hashPassword(signUpRequestDTO.getPassword()));

        User saveduser = userRepository.save(user);
        return modelMapper.map(saveduser,UserDTO.class);
    }

    public String login(LoginRequestDTO loginRequestDTO) throws BadRequestException {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found!"));
        boolean isPasswordMatch = PasswordUtil.checkPassword(loginRequestDTO.getPassword(),user.getPassword());
        if(!isPasswordMatch) throw new BadRequestException("Incorrect password");

        return jwtService.generateAccessToken(user);
    }
}
