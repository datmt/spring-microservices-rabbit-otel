package com.datmt.learning.java.common.dto;

import java.util.List;

public record InventoryOutOfStockEvent(String orderUlid, List<String> unavailableProductUlids) {}
