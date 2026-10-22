package com.example.demo.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Value("${mongo.url}")
    private String mongoUrl;
    @Value("${mongo.db}")
    private String dbName;

    @Bean
    public MongoClient mongoClient() {
        // Hardcodes the client connection connection string straight to your local Mongo
        return MongoClients.create(mongoUrl);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        // Explicitly mandates "my_api_db" as your target database namespace
        return new MongoTemplate(mongoClient(), dbName);
    }
}