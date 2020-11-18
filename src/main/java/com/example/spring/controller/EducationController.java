package com.example.spring.controller;

import com.example.spring.model.Education;
import com.example.spring.service.EducationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/education")
public class EducationController {
    private final EducationService service;

    @PostMapping("/{id}")
    public ResponseEntity<Education> addEducation(@RequestBody Education edu,
                                       @PathVariable("id") UUID userId){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addNewEducation(edu, userId));
    }

    @PutMapping("/")
    public Education updateEducation(@RequestBody Education edu){
        return service.updateEducation(edu);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteEducation(@RequestParam(value = "id")UUID eduId,
                                                  @PathVariable("id") UUID userId){
        service.deleteEducation(eduId,userId);
    }
}
