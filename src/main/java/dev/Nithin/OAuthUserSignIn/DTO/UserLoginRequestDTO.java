package dev.Nithin.OAuthUserSignIn.DTO;

import dev.Nithin.OAuthUserSignIn.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.service.annotation.GetExchange;



public record UserLoginRequestDTO (String email, String password){

    public static User fromUserLoginRequestDTO(UserLoginRequestDTO loginRequestDTO) {
        User user = new User();
        user.setEmail(loginRequestDTO.email.toLowerCase());
        user.setPassword(loginRequestDTO.password);
        return user;
    }
}
