package dev.Nithin.OAuthUserSignIn.service;

import dev.Nithin.OAuthUserSignIn.DTO.AddUserRoleDTO;
import dev.Nithin.OAuthUserSignIn.DTO.UserResponseDTO;
import dev.Nithin.OAuthUserSignIn.entity.Role;
import dev.Nithin.OAuthUserSignIn.entity.User;
import dev.Nithin.OAuthUserSignIn.repository.RoleRepository;
import dev.Nithin.OAuthUserSignIn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service("userDefault")
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public ResponseEntity<UserResponseDTO> getUserDetails(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(UserResponseDTO.fromUser(user.get()), HttpStatus.OK);
    }
    @Override
    public ResponseEntity<UserResponseDTO> getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(UserResponseDTO.fromUser(user.get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserResponseDTO> addRoleToUser(AddUserRoleDTO addUserRoleDTO) {
        Optional<User> user = userRepository.findByEmail(addUserRoleDTO.email());
        if(user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Optional<Role> role1= roleRepository.findByName(addUserRoleDTO.role());
        if(role1.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        User user1 = user.get();
        Role role2 = role1.get();
        user1.getRoles().add(role2);
        userRepository.save(user1);
        return new ResponseEntity<>(UserResponseDTO.fromUser(user1), HttpStatus.OK);
    }


}
