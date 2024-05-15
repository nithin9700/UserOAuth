package dev.Nithin.OAuthUserSignIn.service;


import dev.Nithin.OAuthUserSignIn.DTO.UserLoginRequestDTO;
import dev.Nithin.OAuthUserSignIn.DTO.UserResponseDTO;
import dev.Nithin.OAuthUserSignIn.DTO.UserSignUpRequestDTO;
import dev.Nithin.OAuthUserSignIn.entity.User;
import dev.Nithin.OAuthUserSignIn.exception.InvalidCredentialException;
import dev.Nithin.OAuthUserSignIn.exception.UserNotFoundException;
import dev.Nithin.OAuthUserSignIn.exception.WrongPasswordException;
import dev.Nithin.OAuthUserSignIn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

@Service("UserServiceImp")
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponseDTO signIn(UserSignUpRequestDTO userSignUpRequestDTO) {
        User user = UserSignUpRequestDTO.fromUserRequestDTO(userSignUpRequestDTO);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setIsActive(true);
        return UserResponseDTO.fromUser(userRepository.save(user));
    }

    @Override
    public UserResponseDTO login(UserLoginRequestDTO loginRequestDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = UserLoginRequestDTO.fromUserLoginRequestDTO(loginRequestDTO);
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if(userOptional.isEmpty()) {
            throw new UserNotFoundException("Email is not registered! please signup");
        }
        if(!encoder.matches(loginRequestDTO.password(), user.getPassword())) {
            User userDetails = userOptional.get();
            String token = userDetails.getEmail() + userDetails.getPassword() + 256;
            userDetails.setToken(encoder.encode(token));
            userRepository.save(userDetails);
            return UserResponseDTO.fromUser(userOptional.get());
        }
        throw new WrongPasswordException("Please enter a valid password!");
    }

    @Override
    public boolean validate(String token) {
        Optional<User> user = userRepository.findByToken(token);
        if(user.isPresent()) {
            return true;
        }
        throw  new InvalidCredentialException("Token is Invalid");
    }

    @Override
    public boolean logout(String token) {
        User user = userRepository.findByToken(token).get();
        user.setToken(null);
        userRepository.save(user);
        return true;
    }


}
