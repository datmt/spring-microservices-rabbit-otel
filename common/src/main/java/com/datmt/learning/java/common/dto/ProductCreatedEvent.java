package com.datmt.learning.java.common.dto;

import java.math.BigDecimal;

public record ProductCreatedEvent(String ulid, String name, BigDecimal price) {}
