package dev.Nithin.OAuthUserSignIn.DTO;

import dev.Nithin.OAuthUserSignIn.entity.User;



public record UserLoginRequestDTO (String email, String password){

    public static User fromUserLoginRequestDTO(UserLoginRequestDTO loginRequestDTO) {
        User user = new User();
        user.setEmail(loginRequestDTO.email.toLowerCase());
        user.setPassword(loginRequestDTO.password);
        return user;
    }
}
