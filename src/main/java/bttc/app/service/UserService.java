package bttc.app.service;

import bttc.app.model.*;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ApiResponse> addUser(SystemUser systemUser);
    ResponseEntity<ApiResponse> updateUser(SystemUser systemUser);
    ResponseEntity<ApiResponse> getUser(String userId);
    ResponseEntity<ApiResponse> getAllSystemUsers();
    ResponseEntity<JwtAuthenticationResponse> existsByUsername(String usernameOrEmail, String password);
    ResponseEntity<ApiResponse> saveLoginDetails(User user);
    ResponseEntity<ApiResponse> resetPassword(String email);
    ResponseEntity<ApiResponse> passwordChange(String newPassword);
    ResponseEntity<ApiResponse> saveRegistration(UserLogin userLogin) ;
}
