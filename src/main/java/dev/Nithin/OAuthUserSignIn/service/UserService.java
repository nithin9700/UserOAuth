package dev.Nithin.OAuthUserSignIn.service;


import dev.Nithin.OAuthUserSignIn.DTO.UserResponseDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<UserResponseDTO> getUserDetails();
}
