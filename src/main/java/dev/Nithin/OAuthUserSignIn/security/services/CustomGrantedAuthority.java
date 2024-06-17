package dev.Nithin.OAuthUserSignIn.security.services;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.Nithin.OAuthUserSignIn.entity.Role;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;


@JsonDeserialize
@NoArgsConstructor
public class CustomGrantedAuthority implements GrantedAuthority {

    private String authority;
    public CustomGrantedAuthority(Role role) {
        this.authority = role.getName();
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
