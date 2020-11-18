package com.example.spring.controller;

import com.example.spring.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
@AllArgsConstructor
public class TokenController {
    private final AuthService service;

    @GetMapping(value = "/account-activation/{token}")
    public ResponseEntity<String> verifyToken(@PathVariable(required = true,name = "token") String token){
        if (service.activateAccount(token)){
            return ResponseEntity.status(HttpStatus.OK).body("your account has been activated successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account activation failed");
    }
}
