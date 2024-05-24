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
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service("AuthServiceImp")
public class AuthServiceImp implements AuthService {


    private final String token = "moQBiPOBshRymddqazlugBEsRMzcxcmLuy4NIJEWVzf3MnObFojNRel2mtl3pn";
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthServiceImp(UserRepository userRepository, SessionRepository sessionRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<UserResponseDTO> signIn(UserSignUpRequestDTO userSignUpRequestDTO) {
        User user = UserSignUpRequestDTO.fromUserRequestDTO(userSignUpRequestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsActive(true);
        userRepository.save(user);
        return new ResponseEntity<>(UserResponseDTO.fromUser(userRepository.save(user)), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UserResponseDTO> login(UserLoginRequestDTO loginRequestDTO) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequestDTO.email());
        if(userOptional.isEmpty()) {
            throw new UserNotFoundException("Email is not registered! please signup");
        }
        if(!passwordEncoder.matches(loginRequestDTO.password(), userOptional.get().getPassword())) {
            throw new WrongPasswordException("Wrong password!");
        }
        User userDetails = userOptional.get();
        String token = generateToken(userDetails);
        Session session = new Session();
        session.setToken(token);
        session.setUser(userDetails);
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setExpiryDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10));
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
    public ResponseEntity validate(String token) {
        Optional<Session>  OptionalSession = sessionRepository.findByToken(token);
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
        return new ResponseEntity<>(true,HttpStatus.OK);
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

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000); // Token expires in 1 hour

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, token)
                .compact();
    }
}
