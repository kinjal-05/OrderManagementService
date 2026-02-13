package com.ordermanagement.services;

import com.ordermanagement.dtos.*;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService
{
	OrderResponse createOrder(Long userId, CreateOrderRequest createOrderRequest);
	OrderResponse getOrderById(Long userId, Long orderId);
	Page<OrderResponse> getOrdersForCurrentUser(int page, int size);
	OrderResponse cancelOrderForCurrentUser(Long orderId);
	List<OrderStatusHistoryResponse> getOrderHistoryForCurrentUser(Long orderId);
	Page<OrderResponse> getOrdersByUserId(Long userId, int page, int size);
	Page<OrderResponse> searchOrders(OrderSearchCriteria criteria, int page, int size, String sortBy, String sortDir);
	OrderResponse refundOrder(Long orderId);
	OrderResponse recalculateOrder(Long orderId);
	ResponseEntity<String> deleteOrder(Long orderId);
	OrderPaymentStatusResponse updatePaymentStatus(Long orderId, UpdatePaymentStatusRequest request);
	OrderShippingStatusResponse updateShippingStatus(Long orderId, UpdateShippingStatusRequest request);
}