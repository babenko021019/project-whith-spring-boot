package com.mainacad.service;

import com.mainacad.dao.OrderDAO;
import com.mainacad.dao.mongo.NoSQLOrderDAO;
import com.mainacad.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    OrderDAO orderDAO;

    @Autowired
    NoSQLOrderDAO noSQLOrderDAO;

    public Order getById(Integer id) {
        Optional<Order> order = orderDAO.findById(id);
        if (order.isEmpty()) {
            return null;
        }
        return order.get();
    }

    public Order getByCartWithItem(Integer cartId, Integer itemId) {
        List<Order> orders = getAllByCart(cartId);

        if (orders.isEmpty()) {
            return null;
        } else {
            for (Order each : orders) {
                if (each.getItem().getId().equals(itemId)) {
                    return each;
                }
            }
        }
        return null;
    }

    public int updateAmount(Integer orderId, Integer amount) {
        return orderDAO.updateAmount(orderId, amount);
    }

    public Order save(Order order) {
        if (order.getId() == null) {
            return orderDAO.save(order);
        }
        return null;
    }

    public Order update(Order order) {
        if (order.getId() != null && orderDAO.getOne(order.getId()) != null) {
            return orderDAO.save(order);
        }
        return null;
    }

    public List<Order> getAll() {
        return orderDAO.findAll();
    }

    public List<Order> getAllByCart(Integer cartId) {
        return orderDAO.getAllByCart(cartId);
    }

    public void delete(Order order) {
        orderDAO.delete(order);
    }

    public void delete(Integer id) throws RuntimeException {
        orderDAO.deleteById(id);
    }

    public void saveToMongo(Order order) {
        noSQLOrderDAO.save(order);
    }
}
