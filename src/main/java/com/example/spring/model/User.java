package com.example.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @MongoId(targetType = FieldType.OBJECT_ID)
    private UUID _id;
    @Field(targetType = FieldType.STRING)
    private String email;
    @Field(targetType = FieldType.STRING)
    private String username;
    @Field(targetType = FieldType.STRING)
    private String password;
    @Field(targetType = FieldType.BOOLEAN)
    private boolean activated;
    @Field(targetType = FieldType.DATE_TIME)
    private LocalDate creation;
}
