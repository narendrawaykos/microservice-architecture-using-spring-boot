package com.mysoft.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mysoft.orderservice.common.TransactionRequest;
import com.mysoft.orderservice.common.TransactionResponse;
import com.mysoft.orderservice.entity.Order;
import com.mysoft.orderservice.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService service;
    
//    @PostMapping("/bookOrder")
//    public Order bookOrder(@RequestBody Order request) throws JsonProcessingException {
//        return service.saveOrder(request);
//    }
    
    @PostMapping("/bookOrder")
    public TransactionResponse bookOrder(@RequestBody TransactionRequest request) throws JsonProcessingException {
        return service.saveOrder(request);
    }
}
