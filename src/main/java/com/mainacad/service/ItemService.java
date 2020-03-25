package com.mainacad.service;

import com.mainacad.dao.ItemDAO;
import com.mainacad.dao.mongo.NoSQLItemDAO;
import com.mainacad.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    ItemDAO itemDAO;

    @Autowired
    NoSQLItemDAO noSQLItemDAO;

    public Item getById(Integer id) {
        Optional<Item> cart = itemDAO.findById(id);
        if (cart.isEmpty()) {
            return null;
        }
        return cart.get();
    }

    public List<Item> getAllByCart(Integer cartId) {
        return itemDAO.getAllByCart(cartId);
    }

    public List<Item> getAllAvailable() {
        return itemDAO.getAllAvailable();
    }

    public Item save(Item item) {
        return itemDAO.save(item);
    }

    public List<Item> getAll() {
        return itemDAO.findAll();
    }

    public Item update(Item cart) {
        return itemDAO.save(cart);
    }

    public void delete(Item cart) {
        itemDAO.delete(cart);
    }

    public void delete(Integer id) throws RuntimeException {
        itemDAO.deleteById(id);
    }

    public void saveToMongo(Item item) {
        noSQLItemDAO.save(item);
    }

    public List<Item> getAllFromMongo() {
        return noSQLItemDAO.getAll();
    }
}
