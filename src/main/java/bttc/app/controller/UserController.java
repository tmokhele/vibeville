package bttc.app.controller;

import bttc.app.model.ApiResponse;
import bttc.app.model.SystemUser;
import bttc.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse> addNewUser(@RequestBody SystemUser user) {

        return userService.addUser(user);
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateUser(@RequestBody SystemUser user) {
        return
                userService.updateUser(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable String userId) {

        return userService.getUser(userId);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllNewLoginRequests() {
        return userService.getAllNewLoginRequests();
    }
}
