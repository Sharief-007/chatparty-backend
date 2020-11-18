package com.example.spring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.util.UUID;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Image {
    @MongoId(targetType = FieldType.OBJECT_ID) private UUID _id;
    @Field(targetType = FieldType.STRING) private String filename;
    @Field(targetType = FieldType.TIMESTAMP) private Instant timestamp;
    @Field(targetType = FieldType.BINARY) private Binary body;
}
