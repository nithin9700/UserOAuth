package dev.Nithin.OAuthUserSignIn.service;


import dev.Nithin.OAuthUserSignIn.DTO.AddUserRoleDTO;
import dev.Nithin.OAuthUserSignIn.DTO.UserResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface UserService {

    ResponseEntity<UserResponseDTO> getUserDetails(UUID id);
    ResponseEntity<UserResponseDTO> getUserByEmail(String email);
    ResponseEntity<UserResponseDTO> addRoleToUser(AddUserRoleDTO addUserRoleDTO);
}
