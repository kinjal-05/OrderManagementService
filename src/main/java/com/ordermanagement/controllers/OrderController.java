package com.ordermanagement.controllers;


import com.ordermanagement.dtos.CreateOrderRequest;
import com.ordermanagement.dtos.OrderResponse;
import com.ordermanagement.models.Order;
import com.ordermanagement.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Authenticator;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
//	@PreAuthorize("hashRole('USER')")
	public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request)
	{
//		Long userId=Long.parseLong(authentication.getName());
		Long userId=1L;
		OrderResponse response=orderService.createOrder(userId,request);
		return ResponseEntity.ok(response);
	}
}