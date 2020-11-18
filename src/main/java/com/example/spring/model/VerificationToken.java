package com.example.spring.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.*;

import java.time.LocalDate;

@Document
@Builder
@Data
public class VerificationToken {
    @MongoId(targetType = FieldType.STRING)
    private String token;
    @DBRef(lazy = true)
    private User user;
    @Field(targetType = FieldType.DATE_TIME)
    private LocalDate expiryDate;
}
