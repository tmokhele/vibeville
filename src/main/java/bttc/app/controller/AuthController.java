package bttc.app.controller;

import bttc.app.model.*;
import bttc.app.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Api(value = "AuthController")
public class AuthController {

    @Autowired
    UserService userService;


    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.existsByUsername(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signUp(@Valid @RequestBody SystemUser signUpRequest) {
        return userService.saveLoginDetails(signUpRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody UserLogin userLogin) {
        return userService.saveRegistration(userLogin);
    }

    @PostMapping("/reset")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody String email) {
        return userService.resetPassword(email);
    }

    @PostMapping("/confirm")
    public ResponseEntity confirmPasswordReset(@RequestBody PasswordConfirmation passwordConfirmation) {
        return ResponseEntity.ok().body(userService.confirmPasswordReset(passwordConfirmation));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> updatePassword(@RequestBody String password) {
        return userService.passwordChange(password);
    }
}
