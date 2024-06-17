package dev.Nithin.OAuthUserSignIn.DTO;


import dev.Nithin.OAuthUserSignIn.entity.User;

import java.util.Locale;


public record UserSignUpRequestDTO(String email, String password, String name, String phoneNumber) {

    public static User fromUserRequestDTO(UserSignUpRequestDTO userSignUpRequestDTO) {
        User user = new User();
        user.setEmail(userSignUpRequestDTO.email.toLowerCase());
        user.setPassword(userSignUpRequestDTO.password);
        user.setUsername(userSignUpRequestDTO.name);
        user.setPhoneNumber(userSignUpRequestDTO.phoneNumber);
        return user;
    }
}
