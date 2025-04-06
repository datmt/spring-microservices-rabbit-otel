package com.datmt.learning.java.shipment.repository;

import com.datmt.learning.java.common.helper.BaseRepository;
import com.datmt.learning.java.shipment.model.Shipment;

import java.util.Optional;

public interface ShipmentRepository extends BaseRepository<Shipment> {
    Optional<Shipment> findByOrderUlid(String orderUlid);

}
