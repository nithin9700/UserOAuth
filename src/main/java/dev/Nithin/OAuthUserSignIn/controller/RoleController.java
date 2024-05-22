package dev.Nithin.OAuthUserSignIn.controller;


import dev.Nithin.OAuthUserSignIn.DTO.RoleDTO;
import dev.Nithin.OAuthUserSignIn.DTO.RoleRequestDTO;
import dev.Nithin.OAuthUserSignIn.entity.Role;
import dev.Nithin.OAuthUserSignIn.repository.RoleRepository;
import dev.Nithin.OAuthUserSignIn.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Qualifier("default")
    private final RoleService roleService;
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/createRole")
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleRequestDTO roleRequestDTO) {
        RoleDTO roleDTO = roleService.createRole(roleRequestDTO);
        return new ResponseEntity<>(roleDTO, HttpStatus.OK);
    }
}
