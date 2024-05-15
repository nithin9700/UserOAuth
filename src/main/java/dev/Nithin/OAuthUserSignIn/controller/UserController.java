package dev.Nithin.OAuthUserSignIn.controller;


import dev.Nithin.OAuthUserSignIn.DTO.UserLoginRequestDTO;
import dev.Nithin.OAuthUserSignIn.DTO.UserSignUpRequestDTO;
import dev.Nithin.OAuthUserSignIn.DTO.UserResponseDTO;
import dev.Nithin.OAuthUserSignIn.exception.NoDetailsException;
import dev.Nithin.OAuthUserSignIn.service.UserService;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    @Qualifier("UserServiceImp")
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> signup(@NotNull @RequestBody UserSignUpRequestDTO userSignUpRequestDTO) {
        if(userSignUpRequestDTO == null){
            new NoDetailsException("Please provide the details");
        }
        return ResponseEntity.ok(userService.signIn(userSignUpRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@NotNull @RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        if(userLoginRequestDTO == null){
            new NoDetailsException("Please provide the details");
        }
        return ResponseEntity.ok(userService.login(userLoginRequestDTO));
    }
    @GetMapping("/logout")
    public ResponseEntity<Boolean> logout(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.logout(token));
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.validate(token));
    }



}
