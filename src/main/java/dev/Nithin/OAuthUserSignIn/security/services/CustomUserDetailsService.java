package dev.Nithin.OAuthUserSignIn.security.services;

import dev.Nithin.OAuthUserSignIn.entity.User;
import dev.Nithin.OAuthUserSignIn.repository.UserRepository;
import dev.Nithin.OAuthUserSignIn.security.model.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(username + " not found");
        }
        return new CustomUserDetails(user.get());
    }
}
