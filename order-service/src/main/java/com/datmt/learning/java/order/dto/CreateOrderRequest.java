package com.datmt.learning.java.order.dto;

import java.util.List;

public record CreateOrderRequest(
        List<OrderItemRequest> items
) {
}
