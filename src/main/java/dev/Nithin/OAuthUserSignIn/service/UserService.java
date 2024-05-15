package dev.Nithin.OAuthUserSignIn.service;


import dev.Nithin.OAuthUserSignIn.DTO.UserLoginRequestDTO;
import dev.Nithin.OAuthUserSignIn.DTO.UserSignUpRequestDTO;
import dev.Nithin.OAuthUserSignIn.DTO.UserResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserResponseDTO signIn(UserSignUpRequestDTO userSignUpRequestDTO);
    UserResponseDTO login(UserLoginRequestDTO loginRequestDTO);
    boolean validate(String token);
    boolean logout(String token);

}
