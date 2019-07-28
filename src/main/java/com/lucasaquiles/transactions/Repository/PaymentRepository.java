package com.lucasaquiles.transactions.Repository;

import com.lucasaquiles.transactions.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment save(Payment payment);
}
