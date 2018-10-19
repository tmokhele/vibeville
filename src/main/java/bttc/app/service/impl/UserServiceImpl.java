package bttc.app.service.impl;

import bttc.app.model.ApiResponse;
import bttc.app.model.FirebaseUser;
import bttc.app.model.SystemUser;
import bttc.app.model.UserLogin;
import bttc.app.repository.SystemUserRepository;
import bttc.app.repository.UserRepository;
import bttc.app.service.UserService;
import bttc.app.util.ObjectMappingUtil;
import bttc.app.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Override
    public ResponseEntity<ApiResponse> addUser(SystemUser systemUser) {
        UserLogin userLogin = new UserLogin();
        userLogin.setEmail(systemUser.getEmail());
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
}
