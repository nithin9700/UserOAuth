package dev.Nithin.OAuthUserSignIn.DTO;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;



public record AddUserRoleDTO(String email, String role) {

}
