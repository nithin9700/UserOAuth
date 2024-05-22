package dev.Nithin.OAuthUserSignIn.service;

import dev.Nithin.OAuthUserSignIn.DTO.RoleDTO;
import dev.Nithin.OAuthUserSignIn.DTO.RoleRequestDTO;
import dev.Nithin.OAuthUserSignIn.entity.Role;
import dev.Nithin.OAuthUserSignIn.exception.RoleExits;
import dev.Nithin.OAuthUserSignIn.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("default")
public class RoleServiceImp implements RoleService{
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleDTO createRole(RoleRequestDTO roleRequestDTO) {
        Optional<Role> Optionalrole = roleRepository.findByName(roleRequestDTO.name());
        if(Optionalrole.isPresent()) {
            throw new RoleExits("Role:" +roleRequestDTO.name()+" you want to add is already present in records");
        }
        Role role = RoleRequestDTO.from(roleRequestDTO);
        roleRepository.save(role);
        return RoleDTO.fromRoleLoginRequestDTO(role);
    }
}
