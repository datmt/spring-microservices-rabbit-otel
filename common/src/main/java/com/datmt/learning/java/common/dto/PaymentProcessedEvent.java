package com.datmt.learning.java.common.dto;

import java.math.BigDecimal;

public record PaymentProcessedEvent(String orderUlid, BigDecimal amount) {}
