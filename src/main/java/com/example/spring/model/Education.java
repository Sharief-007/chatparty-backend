package com.example.spring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.UUID;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Education {
    @MongoId(targetType = FieldType.OBJECT_ID) private UUID _id;
    @Field(targetType = FieldType.STRING) private String degree;
    @Field(targetType = FieldType.STRING) private String specalization;
    @Field(targetType = FieldType.STRING) private String university;
    @Field(targetType = FieldType.DATE_TIME) private LocalDate from;
    @Field(targetType = FieldType.DATE_TIME) private LocalDate to;
}
