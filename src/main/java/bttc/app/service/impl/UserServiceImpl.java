package bttc.app.service.impl;

import bttc.app.model.*;
import bttc.app.repository.SystemUserRepository;
import bttc.app.repository.UserRepository;
import bttc.app.security.JwtTokenProvider;
import bttc.app.security.UserPrincipal;
import bttc.app.service.UserService;
import bttc.app.util.ObjectMappingUtil;
import bttc.app.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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



    @Override
    public ResponseEntity<ApiResponse> addUser(SystemUser systemUser) {
        UserLogin userLogin = new UserLogin();
        userLogin.setEmail(systemUser.getEmailAddress());
        userLogin.setPassword(passwordEncoder.encode(PasswordUtil.generatePassword()));
        FirebaseUser firebaseUser = userRepository.saveLoginDetails(userLogin);
        systemUser.setUid(firebaseUser.getLocalId());
        try {
            systemUserRepository.addUser(systemUser);
        }catch (Exception ex)
        {
            userRepository.deleteUser(firebaseUser.getIdToken());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false,"User not registered successfully",systemUser));
        }
        return ResponseEntity.ok().body(new ApiResponse(true, "User registered successfully",systemUser));

    }

    @Override
    public ResponseEntity<ApiResponse> updateUser(SystemUser systemUser)
    {
        return ResponseEntity.ok().body(new ApiResponse(true,"User info updated successfully",systemUserRepository.updateUser(systemUser)));
    }

    @Override
    public ResponseEntity<ApiResponse> getUser(String userId) {

        return ResponseEntity.ok(new ApiResponse(true,"User retrieved successfully",systemUserRepository.getUser(userId)));
    }

    @Override
    public ResponseEntity<ApiResponse> getAllSystemUsers() {
        List<SystemUser> systemUsers = new ArrayList<>();
        CompletableFuture<List<String>> allSystemUsers = systemUserRepository.getAllSystemUsers();
        try {
            for (String s : allSystemUsers.get()) {
                systemUsers.add((SystemUser) ObjectMappingUtil.mapChats(s,SystemUser.class));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(new ApiResponse(true, "All User information retrieved ", systemUsers));
    }

    @Override
    public ResponseEntity<JwtAuthenticationResponse> existsByUsername(String usernameOrEmail, String password) {
        FirebaseAuthResponse firebaseAuthResponse = userRepository.existsByUsername(usernameOrEmail, password);
        User accountInfo = userRepository.getAccountInfo(firebaseAuthResponse.getIdToken(), firebaseAuthResponse.getLocalId());
        accountInfo.setUid(firebaseAuthResponse.getIdToken());
        UserPrincipal userPrincipal = UserPrincipal.create(accountInfo);
        userPrincipal.setUsername(usernameOrEmail);
        userPrincipal.setPassword(password);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok().body(new JwtAuthenticationResponse(tokenProvider.generateToken(authentication)));
    }

    @Override
    public ResponseEntity<ApiResponse> saveLoginDetails(User signUpRequest) {
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

    @Override
    public ResponseEntity<ApiResponse> resetPassword(String email) {
        return ResponseEntity.ok().body(new ApiResponse(true,"Password reset successful",userRepository.resetPassword(email)));
    }

    @Override
    public ResponseEntity<ApiResponse> passwordChange(String newPassword) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(new ApiResponse(true,"Password change successful",userRepository.passwordChange(userPrincipal.getId(),newPassword)));
    }


}