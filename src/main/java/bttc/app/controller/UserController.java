package bttc.app.controller;

import bttc.app.model.ApiResponse;
import bttc.app.model.SystemUser;
import bttc.app.model.UserLogin;
import bttc.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse> addNewUser(@RequestBody SystemUser user) {

        return userService.saveLoginDetails(user);
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
    public ResponseEntity getAllNewLoginRequests() {
        return userService.getAllNewLoginRequests();
    }

    @PostMapping("/remove")
    public ResponseEntity deleteById(@RequestBody UserLogin userLogin){
        boolean t = userService.deleteRequest(userLogin);
        logger.info("deleted record");
        return ResponseEntity.ok().body(t);
    }

    @GetMapping("/all")
    public ResponseEntity getAllSystemUsers() {
        return userService.getAllSystemUsers();
    }
}
