package bttc.app.service.impl;

import bttc.app.model.*;
import bttc.app.repository.SystemUserRepository;
import bttc.app.repository.UserRepository;
import bttc.app.security.JwtTokenProvider;
import bttc.app.security.UserPrincipal;
import bttc.app.service.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    SystemUserRepository systemUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    RestTemplate restTemplate;

    @Value("${vibeville.rabbit.host}")
    private String vibevilleRabbitHost;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public ResponseEntity<ApiResponse> updateUser(SystemUser systemUser) {
        logger.info("edit user");
        return ResponseEntity.ok().body(new ApiResponse(true, "User info updated successfully", systemUserRepository.updateUser(systemUser)));
    }

    @Override
    public ResponseEntity<ApiResponse> getUser(String userId) {

        return ResponseEntity.ok(new ApiResponse(true, "User retrieved successfully", systemUserRepository.getUser(userId)));
    }

    @Override
    public ResponseEntity getAllNewLoginRequests() {
        ResponseEntity<UserLogin[]> entity = restTemplate.exchange(vibevilleRabbitHost, HttpMethod.GET, null, UserLogin[].class);
        return ResponseEntity.ok().body(entity.getBody());
    }

    @Override
    public ResponseEntity<JwtAuthenticationResponse> existsByUsername(String usernameOrEmail, String password) {
        FirebaseAuthResponse firebaseAuthResponse = userRepository.existsByUsername(usernameOrEmail, password);
        SystemUser accountInfo = systemUserRepository.getUser(firebaseAuthResponse.getLocalId());
        accountInfo.setUid(firebaseAuthResponse.getIdToken());
        UserPrincipal userPrincipal = UserPrincipal.create(accountInfo);
        userPrincipal.setUsername(usernameOrEmail);
        userPrincipal.setPassword(password);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        accountInfo.setUid(firebaseAuthResponse.getLocalId());
        return ResponseEntity.ok().body(new JwtAuthenticationResponse(tokenProvider.generateToken(authentication), accountInfo));
    }

    @Override
    public ResponseEntity<ApiResponse> saveLoginDetails(SystemUser signUpRequest) {
        UserLogin userLogin = new UserLogin();
        userLogin.setEmail(signUpRequest.getEmailAddress());
        userLogin.setPassword(!signUpRequest.getPassword().isEmpty()?signUpRequest.getPassword():"password123");
        return getApiResponseResponseEntity(signUpRequest, userLogin);
    }

    private ResponseEntity<ApiResponse> getApiResponseResponseEntity(SystemUser signUpRequest, UserLogin userLogin) {
        FirebaseUser firebaseUser = userRepository.saveLoginDetails(userLogin);
        signUpRequest.setUid(firebaseUser.getLocalId());
        try {
            systemUserRepository.addUser(signUpRequest);
        } catch (Exception ex) {
            userRepository.deleteUser(firebaseUser.getIdToken());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "User not registered successfully", signUpRequest));
        }
        return ResponseEntity.ok().body(new ApiResponse(true, "User registered successfully", signUpRequest));
    }

    @Override
    public ResponseEntity<ApiResponse> resetPassword(String email) {
        return ResponseEntity.ok().body(new ApiResponse(true, "Password reset successful", userRepository.resetPassword(email)));
    }

    @Override
    public ResponseEntity<ApiResponse> passwordChange(String newPassword) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(new ApiResponse(true, "Password change successful", userRepository.passwordChange(userPrincipal.getId(), newPassword)));
    }

    @Override
    public ResponseEntity<ApiResponse> saveRegistration(UserLogin signUpRequest) {
        UserLogin body = null;
        try {
            body = restTemplate.postForEntity(vibevilleRabbitHost, signUpRequest, UserLogin.class).getBody();
        } catch (HttpClientErrorException ex) {
            logger.error("ex :" + ex.getResponseBodyAsString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, ex.getResponseBodyAsString(), signUpRequest));
        }
        logger.info("Registration Results: " + body);
        return ResponseEntity.ok().body(new ApiResponse(true, "User registered successfully", body));
    }

    @Override
    public boolean deleteRequest(UserLogin userLogin) {
        restTemplate.postForEntity(vibevilleRabbitHost + "/remove", userLogin, Object.class);
        return true;
    }

    @Override
    public PasswordChangeResponse confirmPasswordReset(PasswordConfirmation passwordRequest) {
        return userRepository.confirmPasswordRequest(passwordRequest);
    }

    public ResponseEntity getAllSystemUsers() {
        return ResponseEntity.ok(systemUserRepository.getAllSystemUsers());
    }

    @Override
    public boolean deleteUserInformation(SystemUser systemUser) {
        return systemUserRepository.deleteUserInformation(systemUser);
    }

    @Override
    public boolean deleteUserLogin(String uid) throws FirebaseAuthException {
        FirebaseAuth.getInstance().deleteUser(uid);
        return true;
    }


}
