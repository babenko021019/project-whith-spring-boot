package com.mainacad.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "price")
    private Integer price;

    @Column(name = "availability")
    private Integer availability;

    public Item(Integer id, String name, String code, Integer price, Integer availability) {
        super(id);
        this.name = name;
        this.code = code;
        this.price = price;
        this.availability = availability;
    }
}
