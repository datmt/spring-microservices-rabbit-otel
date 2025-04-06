package com.datmt.learning.java.payment.repository;

import com.datmt.learning.java.common.helper.BaseRepository;
import com.datmt.learning.java.payment.model.Payment;

import java.util.Optional;

public interface PaymentRepository extends BaseRepository<Payment> {
    Optional<Payment> findByOrderUlid(String orderUlid);
}
