package dev.Nithin.OAuthUserSignIn.controller;


import dev.Nithin.OAuthUserSignIn.DTO.UserLoginRequestDTO;
import dev.Nithin.OAuthUserSignIn.DTO.UserSignUpRequestDTO;
import dev.Nithin.OAuthUserSignIn.DTO.UserResponseDTO;
import dev.Nithin.OAuthUserSignIn.exception.NoDetailsException;
import dev.Nithin.OAuthUserSignIn.service.AuthService;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    @Qualifier("AuthServiceImp")
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> signup(@NotNull @RequestBody UserSignUpRequestDTO userSignUpRequestDTO) {
        if(userSignUpRequestDTO == null){
            new NoDetailsException("Please provide the details");
        }
        return authService.signIn(userSignUpRequestDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@NotNull @RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        if(userLoginRequestDTO == null){
            throw new NoDetailsException("Please provide the details");
        }

        return authService.login(userLoginRequestDTO);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam("id") UUID userId, @RequestHeader("Authorization") String token) {
        return authService.logout(token, userId);
    }


    @GetMapping("/validate")
    public ResponseEntity<Void> validate(@RequestParam("id") UUID userId, @RequestHeader("Authorization") String token) {
        return authService.validate(token, userId);
    }
}
/*
need to integrate JWT token
 */