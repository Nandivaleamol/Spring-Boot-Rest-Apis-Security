package com.nandivaleamol.controllers;

import java.util.List;
import com.nandivaleamol.entities.User;
import com.nandivaleamol.payloads.ApiResponse;
import com.nandivaleamol.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.createNewUser(user));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Integer userId){
        return ResponseEntity.ok().body(this.userService.getUserByUserId(userId));
    }

    @GetMapping("/")
    private ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok().body(this.userService.getAllUsers());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Integer userId){
        return ResponseEntity.ok().body(this.userService.updateUserByUserId(user,userId));
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
        this.userService.deleteUserByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("User deleted successfully!!",true));
    }

}
