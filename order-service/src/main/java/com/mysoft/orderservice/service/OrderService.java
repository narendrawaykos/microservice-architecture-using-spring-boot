package com.mysoft.orderservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysoft.orderservice.common.Payment;
import com.mysoft.orderservice.common.TransactionRequest;
import com.mysoft.orderservice.common.TransactionResponse;
//import com.mysoft.orderservice.common.TransactionResponse;
import com.mysoft.orderservice.entity.Order;
import com.mysoft.orderservice.repository.OrderRepository;

@Service
@RefreshScope
public class OrderService {

	Logger logger = LoggerFactory.getLogger(OrderService.class);
	@Autowired
	private OrderRepository repository;
	@Autowired
	@Lazy
	private RestTemplate template;

//    public Order saveOrder(Order order) {
//    	return repository.save(order);
//    }

	@Value("${microservices.payment-service.endpoints.endpoint.uri}")
	private String ENDPOINT_URL;

	public TransactionResponse saveOrder(TransactionRequest request) throws JsonProcessingException {
		try {

			String response = "";
			Order order = request.getOrder();
			Payment payment = request.getPayment();
			payment.setOrderId(order.getId());
			payment.setAmount(order.getPrice());
			// rest call
			logger.info("Order-Service Request : " + new ObjectMapper().writeValueAsString(request));
			System.out.println("ENDPOINT_URL "+ENDPOINT_URL);
			Payment paymentResponse = template.postForObject(ENDPOINT_URL, payment, Payment.class);

			response = paymentResponse.getPaymentStatus().equals("success")
					? "payment processing successful and order placed"
					: "there is a failure in payment api , order added to cart";
			logger.info("Order Service getting Response from Payment-Service : "
					+ new ObjectMapper().writeValueAsString(response));
			repository.save(order);
			return new TransactionResponse(order, paymentResponse.getAmount(), paymentResponse.getTransactionId(),
					response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new TransactionResponse(null, 0, null, null);
	}
}
