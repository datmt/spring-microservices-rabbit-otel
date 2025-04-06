package com.datmt.learning.java.inventory.model;

import com.datmt.learning.java.common.model.BaseEntity;
import jakarta.persistence.Entity;

@Entity
public class InventoryItem extends BaseEntity {

    private String productUlid;

    private String productName;

    private Integer availableQuantity;

    private Integer reservedQuantity;

    public boolean isInStock(int requestedQty) {
        return (availableQuantity - reservedQuantity) >= requestedQty;
    }

    public void reserve(int qty) {
        if (!isInStock(qty)) {
            throw new IllegalStateException("Out of stock");
        }
        reservedQuantity += qty;
    }

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

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Integer getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(Integer reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }
}
