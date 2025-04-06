package com.datmt.learning.java.order.dto;

import java.math.BigDecimal;

public class OrderItemDTO {
    private String productUlid;
    private Integer quantity;
    private BigDecimal price; // optional: used for snapshot

    public String getProductUlid() {
        return productUlid;
    }

    public void setProductUlid(String productUlid) {
        this.productUlid = productUlid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
