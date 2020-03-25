package com.mainacad.dao.mongo;

import com.mainacad.model.Order;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoSQLOrderDAO {
    private final MongoDatabase mongoDatabase;

    public void save(Order order){
        MongoCollection collection = mongoDatabase.getCollection("orders");

        Document dbObject = new Document();

        dbObject.put("item", order.getItem());

        collection.insertOne(dbObject);
    }
}

