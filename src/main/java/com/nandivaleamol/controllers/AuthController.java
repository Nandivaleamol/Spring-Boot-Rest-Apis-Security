package com.nandivaleamol.controllers;

import com.nandivaleamol.entities.User;
import com.nandivaleamol.exceptions.ApiException;
import com.nandivaleamol.payloads.JwtAuthRequest;
import com.nandivaleamol.payloads.JwtAuthResponse;
import com.nandivaleamol.security.JwtTokenHelper;
import com.nandivaleamol.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    //login api
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
        this.authenticate(request.getUsername(),request.getPassword());
        var userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
        var token = this.jwtTokenHelper.generateToken(userDetails);
        var response = new JwtAuthResponse();
        response.setToken(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (BadCredentialsException e){
            System.out.println("Invalid Details!!");
            throw new ApiException("Invalid username or password!!");
        }
    }
    // register new user
    @PostMapping("/register")
    public ResponseEntity<User> registerNewUser(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.createNewUser(user));
    }

}

