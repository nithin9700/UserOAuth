package dev.Nithin.OAuthUserSignIn.entity;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "OAuth_Role")
public class Role extends BaseModel{
    private String name;
    private String description;
}
