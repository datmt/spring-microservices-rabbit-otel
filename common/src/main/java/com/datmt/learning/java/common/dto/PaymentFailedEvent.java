package com.datmt.learning.java.common.dto;

public record PaymentFailedEvent(String orderUlid, String reason) {}
