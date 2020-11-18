package com.example.spring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Profile {
    @MongoId(targetType = FieldType.OBJECT_ID) private UUID _id;
    @Field(targetType = FieldType.STRING) private UUID profilePicture;
    @Field(targetType = FieldType.STRING) private String firstname;
    @Field(targetType = FieldType.STRING) private String lastname;
    @Field(targetType = FieldType.DATE_TIME) private LocalDate dateOfBirth;
    @DBRef private List<Education> education;
    @DBRef private List<Work> works;
}
