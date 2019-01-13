package bttc.app.service;

import bttc.app.model.*;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ApiResponse> updateUser(SystemUser systemUser);
    ResponseEntity<ApiResponse> getUser(String userId);
    ResponseEntity getAllNewLoginRequests();
    ResponseEntity<JwtAuthenticationResponse> existsByUsername(String usernameOrEmail, String password);
    ResponseEntity<ApiResponse> saveLoginDetails(SystemUser user);
    ResponseEntity<ApiResponse> resetPassword(String email);
    ResponseEntity<ApiResponse> passwordChange(String newPassword);
    ResponseEntity<ApiResponse> saveRegistration(UserLogin userLogin) ;
    boolean deleteRequest(UserLogin userLogin);
    PasswordChangeResponse confirmPasswordReset(PasswordConfirmation passwordRequest);
    ResponseEntity getAllSystemUsers();
}
