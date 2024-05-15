package dev.Nithin.OAuthUserSignIn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class OAuthUserSignInApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuthUserSignInApplication.class, args);
	}

}
