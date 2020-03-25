package com.mainacad.service;

import com.mainacad.AppRunner;
import com.mainacad.model.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(AppRunner.class)
@ActiveProfiles("test")
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Test
    void saveToMongo() {
        Item item = new Item();
        item.setName("test name");
        item.setCode("test code");
        item.setPrice(12345);

        itemService.saveToMongo(item);

        List<Item> items = itemService.getAllFromMongo();

        assertNotNull(items);
        assertTrue(items.size() >= 1);
    }
}