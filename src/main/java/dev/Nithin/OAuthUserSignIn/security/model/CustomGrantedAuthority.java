package dev.Nithin.OAuthUserSignIn.security.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.Nithin.OAuthUserSignIn.entity.Role;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;


@JsonDeserialize
@NoArgsConstructor
public class CustomGrantedAuthority implements GrantedAuthority {
    private String role;
    public CustomGrantedAuthority(Role role) {
        this.role = role.getName();
    }

    @Override
    public String getAuthority() {
        return this.role;
    }
}
