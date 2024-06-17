package dev.Nithin.OAuthUserSignIn;

import dev.Nithin.OAuthUserSignIn.security.Repository.JpaRegisteredClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.UUID;


@SpringBootTest
public class InsertReginstedClientDB {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JpaRegisteredClientRepository registeredClientRepository;
    @Test
    public void testInsertClient() {
//
//        RegisteredClient postman = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("postman")
//                .clientSecret(passwordEncoder.encode("postmanpassword"))
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                .redirectUri("https://oauth.pstmn.io/v1/callback")
//                .postLogoutRedirectUri("http://127.0.0.1:8080/")
//                .scope(OidcScopes.OPENID)
//                .scope(OidcScopes.PROFILE)
//                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
//                .build();
//        registeredClientRepository.save(postman);
    }
}
