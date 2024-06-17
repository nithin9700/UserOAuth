package dev.Nithin.OAuthUserSignIn.service;


import dev.Nithin.OAuthUserSignIn.DTO.UserLoginRequestDTO;
import dev.Nithin.OAuthUserSignIn.DTO.UserSignUpRequestDTO;
import dev.Nithin.OAuthUserSignIn.DTO.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface AuthService {
    ResponseEntity<UserResponseDTO> signIn(UserSignUpRequestDTO userSignUpRequestDTO);
    ResponseEntity<UserResponseDTO> login(UserLoginRequestDTO loginRequestDTO);
    ResponseEntity<Boolean> validate(String token);
    ResponseEntity<Void> logout(String token, UUID userId);

}
