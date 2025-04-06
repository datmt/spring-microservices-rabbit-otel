package com.datmt.learning.java.common.dto;

import java.util.List;

public record InventoryReservedEvent(String orderUlid, List<Item> items) {
    public record Item(String productUlid, int quantity) {}
}
