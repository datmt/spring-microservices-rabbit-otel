package com.datmt.learning.java.shipment.model;

import com.datmt.learning.java.common.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity()
@Table(name = "shipments")
public class Shipment extends BaseEntity {

    private String orderUlid;

    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;
    public Shipment() {}

    public Shipment(String orderUlid, String trackingNumber, ShipmentStatus status) {
        this.orderUlid = orderUlid;
        this.trackingNumber = trackingNumber;
        this.status = status;
    }

    public String getOrderUlid() {
        return orderUlid;
    }

    public void setOrderUlid(String orderUlid) {
        this.orderUlid = orderUlid;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }
}

