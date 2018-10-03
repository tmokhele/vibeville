package bttc.app.controller;

import bttc.app.model.*;
import bttc.app.repository.RoleRepository;
import bttc.app.repository.UserRepository;
import bttc.app.security.JwtTokenProvider;
import bttc.app.security.UserPrincipal;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Api(value = "AuthController", description = "Operations related to AuthController API.")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        FirebaseAuthResponse firebaseAuthResponse = userRepository.existsByUsername(loginRequest.getUsername(), loginRequest.getPassword());
        User accountInfo = userRepository.getAccountInfo(firebaseAuthResponse.getIdToken(), firebaseAuthResponse.getLocalId());
        UserPrincipal userPrincipal = UserPrincipal.create(accountInfo);
        userPrincipal.setUsername(loginRequest.getUsername());
        userPrincipal.setPassword(loginRequest.getPassword());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody User signUpRequest) throws Throwable {
        UserLogin userLogin = new UserLogin();
        userLogin.setEmail(signUpRequest.getEmail());
        userLogin.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        FirebaseUser firebaseUser = userRepository.saveLoginDetails(userLogin);
        signUpRequest.setUid(firebaseUser.getLocalId());
        try {
            userRepository.saveAdditionalUserDetails(signUpRequest);
        }catch (Exception ex)
        {
            userRepository.deleteUser(firebaseUser.getIdToken());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false,"User not registered successfully",signUpRequest));
        }
        return ResponseEntity.ok().body(new ApiResponse(true, "User registered successfully",signUpRequest));
    }

}
