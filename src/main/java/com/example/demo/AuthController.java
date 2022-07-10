package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/api/auth/user")
    public ResponseEntity<UserRegistrationResponse> registerUser(@RequestBody Person person) {
        return new ResponseEntity<>(userDetailsService.registerUser(person), HttpStatus.CREATED);
    }

    @GetMapping("/api/auth/list")
    public List<UserListView> showAllUsers() {
        return userDetailsService.showAllUsers();
    }

    @DeleteMapping("/api/auth/user/{username}")
    public ResponseEntity<UserDeletionResponse> deleteUser(@PathVariable String username) {
        return new ResponseEntity<>(userDetailsService.deleteUser(username), HttpStatus.OK);
    }

    @DeleteMapping("/api/auth/user")
    public ResponseEntity<UserDeletionResponse> deleteUser(@RequestBody Map<String, String> unlockRequest) {
        return new ResponseEntity<>(userDetailsService.deleteUser(unlockRequest.get("username")), HttpStatus.OK);
    }

    @PutMapping("/api/auth/role")
    public ResponseEntity<UserRegistrationResponse> grantRole(@RequestBody Map<String, String> newRole) {
        return new ResponseEntity<>(userDetailsService.grantRole(newRole), HttpStatus.OK);
    }

    @PutMapping("/api/auth/access")
    public ResponseEntity<Map<String, String>> unlockUser(@RequestBody Map<String, String> unlockRequest) {
        return new ResponseEntity<>(userDetailsService.unlockUser(unlockRequest), HttpStatus.OK);
    }

}
