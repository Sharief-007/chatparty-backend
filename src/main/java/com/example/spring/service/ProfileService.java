package com.example.spring.service;

import com.example.spring.model.Profile;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProfileService {
    private final MongoOperations operations;
    
    public Profile find(UUID id){
         return operations.findOne(Query.query(Criteria.where("_id").is(id)),Profile.class);
    }

    public List<Profile> findAll(){
        return operations.findAll(Profile.class);
    }
}
