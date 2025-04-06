package com.datmt.learning.java.inventory.repository;

import com.datmt.learning.java.common.helper.BaseRepository;
import com.datmt.learning.java.inventory.model.InventoryItem;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends BaseRepository<InventoryItem> {
    Optional<InventoryItem> findByProductUlid(String productUlid);
    List<InventoryItem> findAllByProductUlidIn(List<String> productUlids);
}
