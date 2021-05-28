package com.mysoft.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysoft.orderservice.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Integer> {
}
