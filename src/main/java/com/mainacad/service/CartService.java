package com.mainacad.service;

import com.mainacad.dao.CartDAO;
import com.mainacad.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    CartDAO cartDAO;

    @Autowired
    CartService cartService;

    @Autowired
    ItemService itemService;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;


    public Cart getById(Integer id) {
        Optional<Cart> cart = cartDAO.findById(id);
        if (cart.isEmpty()) {
            return null;
        }
        return cart.get();
    }

    public Cart save(Cart cart) {
        if (cart.getId() == null) {
            return cartDAO.save(cart);
        }
        return null;
    }

    public int updateStatus(Integer cartId, Status status) {
        return cartDAO.updateStatus(cartId, status.ordinal());
    }

    public Cart addItem(Integer userId, Integer itemId) {
        User user = userService.getById(userId);
        if (user == null) {
            return null;
        }

        Item item = itemService.getById(itemId);
        if (item == null) {
            return null;
        }

        List<Cart> carts = cartDAO.getByUserAndOpenStatus(userId);
        Cart openCart;
        if (carts.size() == 0) {
            Long currentTime = new Date().getTime();
            openCart = new Cart(Status.OPEN, user, currentTime);
            cartService.save(openCart);
            Order order = new Order(item, openCart, 1);
            orderService.save(order);
        } else {
            openCart = carts.get(0);
            Order targetOrder = orderService.getByCartWithItem(openCart.getId(), itemId);
            if (targetOrder == null) {
                Order order = new Order(item, openCart, 1);
                orderService.save(order);
            } else {
                orderService.updateAmount(targetOrder.getId(),
                        orderService.getById(targetOrder.getId()).getAmount() + 1);
            }
        }
        return openCart;
    }

    public List<Cart> getAll() {
        return cartDAO.findAll();
    }


    public List<Cart> getAllByUserAndPeriod(Integer userId, Long timeFrom, Long timeTo) {
        return cartDAO.getAllByUserAndPeriod(userId, timeFrom, timeTo);
    }

    public List<Cart> getAllByUser(Integer userId) {
        return cartDAO.getAllByUser(userId);
    }

    public List<Cart> getByUserAndOpenStatus(Integer userId) {
        return cartDAO.getByUserAndOpenStatus(userId);
    }

    public void delete(Cart cart) {
        cartDAO.delete(cart);
    }

    public void delete(Integer id) throws RuntimeException {
        cartDAO.deleteById(id);
    }
}
