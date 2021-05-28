package com.mysoft.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysoft.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
    Payment findByOrderId(int orderId);
}

