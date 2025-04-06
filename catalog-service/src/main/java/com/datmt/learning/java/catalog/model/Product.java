package com.datmt.learning.java.catalog.model;

import com.datmt.learning.java.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity()
@Table(name = "products")
public class Product extends BaseEntity {
    private String name;
    private BigDecimal price;

    @Column(columnDefinition = "TEXT")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
