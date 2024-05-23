package dev.Nithin.OAuthUserSignIn.controller;


import dev.Nithin.OAuthUserSignIn.DTO.AddUserRoleDTO;
import dev.Nithin.OAuthUserSignIn.DTO.UserResponseDTO;
import dev.Nithin.OAuthUserSignIn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserDetails(@RequestParam UUID id) {
        return userService.getUserDetails(id);
    }

    @PutMapping("/addrole")
    public ResponseEntity<UserResponseDTO> addRole(@RequestBody AddUserRoleDTO addUserRoleDTO) {
        return userService.addRoleToUser(addUserRoleDTO);
    }
}

