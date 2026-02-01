package com.divyansh.linkedin.user_service.controller;

import com.divyansh.linkedin.user_service.dto.LoginRequestDTO;
import com.divyansh.linkedin.user_service.dto.SignUpRequestDTO;
import com.divyansh.linkedin.user_service.dto.UserDTO;
import com.divyansh.linkedin.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) throws BadRequestException {
        UserDTO userDTO = authService.signUp(signUpRequestDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO) throws BadRequestException {
        String token = authService.login(loginRequestDTO);
        return ResponseEntity.ok(token);
    }
}

