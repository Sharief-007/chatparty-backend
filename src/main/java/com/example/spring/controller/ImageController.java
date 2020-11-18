package com.example.spring.controller;

import com.example.spring.model.Image;
import com.example.spring.model.Profile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;


@RestController
@AllArgsConstructor
@RequestMapping("/profilepicture")
@Slf4j
public class ImageController {
    private final MongoOperations operations;

    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity upload(@RequestParam(value = "file",required = true) MultipartFile file,
                                 @RequestParam(value = "profile",required = true) UUID id) throws IOException {
        var image= Image.builder()
                ._id(UUID.randomUUID())
                .filename(file.getOriginalFilename())
                .timestamp(Instant.now())
                .body(new Binary(file.getBytes()))
                .build();
        image = operations.save(image);
        log.info("Uploaded successfully :"+image.getFilename());
        var query = Query.query(Criteria.where("_id").is(id));
        var update = BasicUpdate.update("profilePicture",id);
        operations.upsert(query,update, Profile.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(image.get_id());
    }

    @GetMapping("/{id}")
    public byte[] getImage(@PathVariable(value = "id",required = true) UUID uuid){
        var q = Query.query(Criteria.where("_id").is(uuid));
        var image = operations.findOne(q,Image.class);
        if (Objects.nonNull(image)){
            return image.getBody().getData();
        }else return new byte[]{};
    }
}

