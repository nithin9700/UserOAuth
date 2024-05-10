package dev.Nithin.OAuthUserSignIn.service;


import dev.Nithin.OAuthUserSignIn.DTO.UserLoginRequestDTO;
import dev.Nithin.OAuthUserSignIn.DTO.UserResponseDTO;
import dev.Nithin.OAuthUserSignIn.DTO.UserSignUpRequestDTO;
import dev.Nithin.OAuthUserSignIn.entity.User;
import dev.Nithin.OAuthUserSignIn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserServiceImp")
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponseDTO signIn(UserSignUpRequestDTO userSignUpRequestDTO) {
        User user = UserSignUpRequestDTO.fromUserRequestDTO(userSignUpRequestDTO);
        return null;
    }

    @Override
    public UserResponseDTO login(UserLoginRequestDTO loginRequestDTO) {
        User user = UserLoginRequestDTO.fromUserLoginRequestDTO(loginRequestDTO);
        return null;
    }
}
