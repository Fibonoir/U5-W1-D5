package it.epicode.bookings_manager.controller;

import it.epicode.bookings_manager.dto.UserResponse;
import it.epicode.bookings_manager.entity.User;
import it.epicode.bookings_manager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody User user) {
        User created = userService.createUser(user);
        UserResponse response = new UserResponse();
        response.setUsername(created.getUsername());
        response.setFullName(created.getFullName());
        response.setEmail(created.getEmail());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String username) {
        User u = userService.getByUsername(username);
        UserResponse response = new UserResponse();
        response.setUsername(u.getUsername());
        response.setFullName(u.getFullName());
        response.setEmail(u.getEmail());
        return ResponseEntity.ok(response);
    }
}
