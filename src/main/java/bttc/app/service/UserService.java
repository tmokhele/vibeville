package bttc.app.service;

import bttc.app.model.ApiResponse;
import bttc.app.model.SystemUser;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ApiResponse> addUser(SystemUser systemUser);
    ResponseEntity<ApiResponse> updateUser(SystemUser systemUser);
    ResponseEntity<ApiResponse> getUser(String userId);
    ResponseEntity<ApiResponse> getAllSystemUsers();
}
