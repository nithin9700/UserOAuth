package dev.Nithin.OAuthUserSignIn.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.Nithin.OAuthUserSignIn.DTO.UserLoginRequestDTO;
import dev.Nithin.OAuthUserSignIn.DTO.UserResponseDTO;
import dev.Nithin.OAuthUserSignIn.DTO.UserSignUpRequestDTO;
import dev.Nithin.OAuthUserSignIn.client.KafkaProducerClient;
import dev.Nithin.OAuthUserSignIn.entity.Session;
import dev.Nithin.OAuthUserSignIn.entity.SessionStatus;
import dev.Nithin.OAuthUserSignIn.entity.User;
import dev.Nithin.OAuthUserSignIn.exception.InvalidCredentialException;
import dev.Nithin.OAuthUserSignIn.exception.UserNotFoundException;
import dev.Nithin.OAuthUserSignIn.exception.WrongPasswordException;
import dev.Nithin.OAuthUserSignIn.repository.SessionRepository;
import dev.Nithin.OAuthUserSignIn.repository.UserRepository;
import dev.Nithin.OAuthUserSignIn.utiles.TokenManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import java.util.*;

@Service("AuthServiceImp")
public class AuthServiceImp implements AuthService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaProducerClient kafkaProducerClient;
    private final ObjectMapper objectMapper;
    private final TokenManager tokenManager;


    public AuthServiceImp(UserRepository userRepository, SessionRepository sessionRepository,PasswordEncoder passwordEncoder,
                          KafkaProducerClient kafkaProducerClient, ObjectMapper objectMapper, TokenManager tokenManager) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.passwordEncoder = passwordEncoder;
        this.kafkaProducerClient = kafkaProducerClient;
        this.objectMapper = objectMapper;
        this.tokenManager = tokenManager;
    }

    @Override
    public ResponseEntity<UserResponseDTO> signIn(UserSignUpRequestDTO userSignUpRequestDTO) {
        User user = UserSignUpRequestDTO.fromUserRequestDTO(userSignUpRequestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsActive(true);
        userRepository.save(user);
        UserResponseDTO userResponseDTO = UserResponseDTO.fromUser(userRepository.save(user));
        try {
            kafkaProducerClient.sendMessage("sendMail", objectMapper.writeValueAsString(userResponseDTO));
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
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
        String token = tokenManager.getToken(userDetails);
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
    public ResponseEntity<Boolean> validate(String token, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        boolean status = tokenManager.validateJwtToken(token, user);
        if(status){
            return new ResponseEntity<>(true,HttpStatus.OK);
        }
        return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
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
