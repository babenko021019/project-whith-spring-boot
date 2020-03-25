package com.mainacad.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @ManyToOne(targetEntity = Item.class)
    private Item item;

    @ManyToOne(targetEntity = Cart.class)
    private Cart cart;

    @Column(name = "amount")
    private Integer amount;

    public Order(Integer id, Item item, Cart cart, Integer amount) {
        super(id);
        this.item = item;
        this.cart = cart;
        this.amount = amount;
    }
}
