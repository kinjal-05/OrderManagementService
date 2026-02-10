package com.ordermanagement.services;

import com.ordermanagement.dtos.CreateOrderRequest;
import com.ordermanagement.dtos.OrderResponse;

public interface OrderService
{
	OrderResponse createOrder(Long userId, CreateOrderRequest createOrderRequest);
}