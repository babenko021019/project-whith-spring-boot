package com.mainacad.dao.mongo;

import com.mainacad.model.Item;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NoSQLItemDAO {

    private final MongoDatabase mongoDatabase;

    public void save(Item item){
        MongoCollection collection = mongoDatabase.getCollection("items");

        Document dbObject = new Document();

        dbObject.put("code", item.getCode());
        dbObject.put("name", item.getName());
        dbObject.put("price", item.getPrice());
        dbObject.put("availability", item.getAvailability());

        collection.insertOne(dbObject);
    }


    public List<Item> getAll(){
        MongoCollection collection = mongoDatabase.getCollection("items");

        List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());
        List <Item> items = new ArrayList<>();
        for (Document document : documents) {
            Item item = new Item();
            item.setCode(document.getString("code"));
            item.setName(document.getString("name"));
            item.setPrice((Integer) document.get("price"));
            items.add(item);
        }
        return items;
    }
}
