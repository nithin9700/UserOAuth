package dev.Nithin.OAuthUserSignIn.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "OAuth_User")
public class User extends BaseModel {
    private String username;
    private String email;
    private String password;
    private Boolean isActive;
    private String phoneNumber;
    @ManyToMany
    private List<Role> roles;
    private String token;

}
