// package com.kdu.eventsphere.controller;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.kdu.eventsphere.dto.request.CreateUserRequest;
// import com.kdu.eventsphere.dto.response.UserResponse;
// import com.kdu.eventsphere.service.UserService;

// import jakarta.validation.Valid;

// @RestController
// @RequestMapping("/api/v1/users")
// public class UserController {
    
//     private final UserService userService;

//     public UserController(UserService userService) {
//         this.userService = userService;
//     }

//     @PostMapping
//     @PreAuthorize("hasRole('LIBRARIAN')")
//     public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
//         UserResponse userResponse = userService.createUser(request);
//         return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
//     }
// }
