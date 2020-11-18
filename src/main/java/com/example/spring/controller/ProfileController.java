package com.example.spring.controller;

import com.example.spring.model.Profile;
import com.example.spring.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/profile")
@AllArgsConstructor
public class ProfileController {
    private final ProfileService service;

    @GetMapping("/")
    public List<Profile> getAllProfiles(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Profile getMyProfile(@PathVariable(value = "id")UUID uuid){
        return service.find(uuid);
    }
}
