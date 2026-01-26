package com.kdu.smarthome.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdu.smarthome.domain.user.User;
import com.kdu.smarthome.services.UserService;

@RestController
@RequestMapping(ApiEndpoints.User.BASE)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> addUser(
        @Validated @RequestBody String username,
        @Validated @RequestBody String password
    ){

        User user = userService.addUser(username, password);
        return ResponseEntity.ok(user);
    }

    
}
