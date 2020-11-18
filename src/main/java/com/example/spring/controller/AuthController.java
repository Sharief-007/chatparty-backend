package com.example.spring.controller;

import com.example.spring.dto.JwtResponse;
import com.example.spring.dto.LoginRequest;
import com.example.spring.dto.RegisterRequest;
import com.example.spring.model.User;
import com.example.spring.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {
    private final AuthService service;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseEntity<User> signUp(@RequestBody RegisterRequest request) {
        var user = service.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity<JwtResponse> registration(@RequestBody LoginRequest request){
        var token = service.login(request);
        return ResponseEntity.ok(new JwtResponse(token));
    }

}
