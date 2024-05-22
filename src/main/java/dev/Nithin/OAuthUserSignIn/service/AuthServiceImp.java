package dev.Nithin.OAuthUserSignIn.service;


import dev.Nithin.OAuthUserSignIn.DTO.UserLoginRequestDTO;
import dev.Nithin.OAuthUserSignIn.DTO.UserResponseDTO;
import dev.Nithin.OAuthUserSignIn.DTO.UserSignUpRequestDTO;
import dev.Nithin.OAuthUserSignIn.entity.Session;
import dev.Nithin.OAuthUserSignIn.entity.SessionStatus;
import dev.Nithin.OAuthUserSignIn.entity.User;
import dev.Nithin.OAuthUserSignIn.exception.InvalidCredentialException;
import dev.Nithin.OAuthUserSignIn.exception.UserNotFoundException;
import dev.Nithin.OAuthUserSignIn.exception.WrongPasswordException;
import dev.Nithin.OAuthUserSignIn.repository.SessionRepository;
import dev.Nithin.OAuthUserSignIn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service("AuthServiceImp")
public class AuthServiceImp implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;


    @Override
    public ResponseEntity<UserResponseDTO> signIn(UserSignUpRequestDTO userSignUpRequestDTO) {
        User user = UserSignUpRequestDTO.fromUserRequestDTO(userSignUpRequestDTO);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setIsActive(true);
        userRepository.save(user);
        return new ResponseEntity<>(UserResponseDTO.fromUser(userRepository.save(user)), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UserResponseDTO> login(UserLoginRequestDTO loginRequestDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Optional<User> userOptional = userRepository.findByEmail(loginRequestDTO.email());
        if(userOptional.isEmpty()) {
            throw new UserNotFoundException("Email is not registered! please signup");
        }
        if(!encoder.matches(loginRequestDTO.password(), userOptional.get().getPassword())) {
            throw new WrongPasswordException("Wrong password!");
        }
        User user = UserLoginRequestDTO.fromUserLoginRequestDTO(loginRequestDTO);
        User userDetails = userOptional.get();
        String token = userDetails.getEmail() + userDetails.getPassword() + 256;
        Session session = new Session();
        session.setToken(token);
        session.setUser(userDetails);
        session.setSessionStatus(SessionStatus.ACTIVE);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime newDate = now.plusDays(10);
        Date date = Date.from(newDate.atZone(ZoneId.systemDefault()).toInstant());
        session.setExpiryDate(date);
        sessionRepository.save(session);

        MultiValueMap<String, String> claims = new MultiValueMapAdapter<>(new HashMap<>());
        claims.add("AUTH_TOKEN", token);
        ResponseEntity<UserResponseDTO> response = new ResponseEntity<>(
                UserResponseDTO.fromUser(userDetails),
                claims,
                HttpStatus.OK
        );
        return response;
    }

    @Override
    public ResponseEntity<Void> validate(String token, UUID userId) {
        Optional<Session>  OptionalSession = sessionRepository.findByTokenAndUserId(token, userId);
        if(OptionalSession.isEmpty()){
            throw  new InvalidCredentialException("Token is Invalid");
        }

        Session session = OptionalSession.get();
        if(!session.getToken().equals(token)){
            throw new InvalidCredentialException("Token is Invalid");
        }
        if(session.getExpiryDate().getTime() < System.currentTimeMillis()){
            throw new InvalidCredentialException("Token has Expired");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> logout(String token, UUID userId) {
        Optional<Session>  session = sessionRepository.findByTokenAndUserId(token, userId);
        if(session.isPresent()) {
            Session session1 = session.get();
            session1.setSessionStatus(SessionStatus.EXPIRED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
