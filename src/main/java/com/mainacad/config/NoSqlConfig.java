package com.mainacad.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NoSqlConfig {

    @Value("${no-sql.mongo.host:localhost}")
    private String host;

    @Value("${no-sql.mongo.port:27017}")
    private Integer port;

    @Value("${no-sql.mongo.name:local}")
    private String dbName;

    @Bean
    MongoDatabase getMongoDatabase(){
        MongoClient mongo = new MongoClient(host, port);
        return mongo.getDatabase(dbName);
    }
}
