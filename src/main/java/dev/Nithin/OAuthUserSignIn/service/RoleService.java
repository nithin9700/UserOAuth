package dev.Nithin.OAuthUserSignIn.service;

import dev.Nithin.OAuthUserSignIn.DTO.RoleDTO;
import dev.Nithin.OAuthUserSignIn.DTO.RoleRequestDTO;
import org.springframework.http.ResponseEntity;

public interface RoleService {
    ResponseEntity<RoleDTO> createRole(RoleRequestDTO  roleRequestDTO);
}
