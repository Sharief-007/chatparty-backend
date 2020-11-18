package com.example.spring.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

@Configuration
public class MongoConfiguration extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongodb_uri;

    @Value("${spring.data.mongodb.database}")
    private String mongo_database;

    @Override
    protected String getDatabaseName() {
        return mongo_database;
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(getSettings());
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(),getDatabaseName());
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws ClassNotFoundException {
        return new GridFsTemplate(mongoDbFactory(),mappingMongoConverter(mongoDbFactory(),customConversions(),mongoMappingContext(customConversions())));
    }

    @Override
    @Bean
    public MappingMongoConverter mappingMongoConverter(@Qualifier("mongoDatabaseFactory") MongoDatabaseFactory databaseFactory, MongoCustomConversions customConversions, MongoMappingContext mappingContext) {
        return super.mappingMongoConverter(databaseFactory, customConversions, mappingContext);
    }

    private MongoClientSettings getSettings(){
        return MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongodb_uri))
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .build();
    }
}
