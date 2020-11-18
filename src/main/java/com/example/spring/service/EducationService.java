package com.example.spring.service;

import com.example.spring.model.Education;
import com.example.spring.model.Profile;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class EducationService {
    private final MongoOperations operations;

    @Transactional
    public Education addNewEducation(Education education, UUID userId){
        education.set_id(UUID.randomUUID());
        education = operations.save(education);
        addReference(education,userId);
        return education;
    }

    @Transactional
    public Education updateEducation(Education education){
        var query = Query.query(Criteria.where("_id").is(education.get_id()));
        return operations.findAndReplace(query, education);
    }

    @Transactional
    public void deleteEducation(UUID eduId,UUID userId){
        operations.remove(Query.query(Criteria.where("_id").is(eduId)),Education.class);
        var query = Query.query(Criteria.where("_id").is(userId));
        var update = new Update().pull("education",Education.builder()._id(eduId).build());
        operations.updateFirst(query,update,Profile.class);
    }

    @Async
    @Transactional
    protected void addReference(Education education, UUID userId) {
        var query = Query.query(Criteria.where("_id").is(userId));
        var update = new Update().push("education").value(education);
        operations.updateFirst(query,update, Profile.class);
    }
}
