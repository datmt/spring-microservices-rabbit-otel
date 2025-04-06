package com.datmt.learning.java.order.model;

import com.datmt.learning.java.common.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity()
@Table(name = "order_items")
public class OrderItem extends BaseEntity {

    private String productUlid; // Reference to catalog-service Product

    private String productName; // Snapshot in case name changes later

    private BigDecimal price; // Unit price at time of order

    private Integer quantity;

    public BigDecimal calculateSubtotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    // Getters and Setters

    public String getProductUlid() {
        return productUlid;
    }

    public void setProductUlid(String productUlid) {
        this.productUlid = productUlid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
