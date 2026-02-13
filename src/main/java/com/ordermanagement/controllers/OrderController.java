package com.ordermanagement.controllers;


import com.ordermanagement.dtos.*;
import com.ordermanagement.models.Order;
import com.ordermanagement.models.OrderStatus;
import com.ordermanagement.models.PaymentStatus;
import com.ordermanagement.models.ShippingStatus;
import com.ordermanagement.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.Authenticator;
import java.time.LocalDateTime;
import java.util.List;

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

	@GetMapping("/{id}")
	public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id)
	{
//		Long userId = userDetails.getId(); // get logged-in user's ID
		Long userId=1L;
		OrderResponse response = orderService.getOrderById(userId, id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/my-orders")
	public ResponseEntity<Page<OrderResponse>> getMyOrders(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size)
	{

		Page<OrderResponse> responses = orderService.getOrdersForCurrentUser(page, size);
		return ResponseEntity.ok(responses);
	}

	@PutMapping("/{id}/cancel")
	public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long id)
	{
		OrderResponse response = orderService.cancelOrderForCurrentUser(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}/history")
	public ResponseEntity<List<OrderStatusHistoryResponse>> getOrderHistory(@PathVariable Long id)
	{
		List<OrderStatusHistoryResponse> history = orderService.getOrderHistoryForCurrentUser(id);
		return ResponseEntity.ok(history);
	}
	@GetMapping("/user/{userId}/orders")
	public ResponseEntity<Page<OrderResponse>> getOrdersByUserId(
			@PathVariable Long userId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size
	)
	{
//		if (!authService.isAdmin()) {
//			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
//		}

		Page<OrderResponse> orders = orderService.getOrdersByUserId(userId, page, size);
		return ResponseEntity.ok(orders);
	}

	@GetMapping("/admin/search")
	public ResponseEntity<Page<OrderResponse>> searchOrders(
			@RequestParam(required = false) Long userId,
			@RequestParam(required = false) String orderStatus,       // Receive as String
			@RequestParam(required = false) String paymentStatus,     // Receive as String
			@RequestParam(required = false) String shippingStatus,    // Receive as String
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
			@RequestParam(required = false) BigDecimal minTotalAmount,
			@RequestParam(required = false) BigDecimal maxTotalAmount,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "createdAt") String sortBy,
			@RequestParam(defaultValue = "desc") String sortDir
	)
	{
		// Uncomment in production to restrict access
//    if (!authService.isAdmin()) {
//        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
//    }
		OrderStatus oStatus = orderStatus != null ? OrderStatus.valueOf(orderStatus.toUpperCase()) : null;
		PaymentStatus pStatus = paymentStatus != null ? PaymentStatus.valueOf(paymentStatus.toUpperCase()) : null;
		ShippingStatus sStatus = shippingStatus != null ? ShippingStatus.valueOf(shippingStatus.toUpperCase()) : null;

		OrderSearchCriteria criteria = new OrderSearchCriteria();
		criteria.setUserId(userId);
		criteria.setOrderStatus(oStatus);
		criteria.setPaymentStatus(pStatus);
		criteria.setShippingStatus(sStatus);
		criteria.setStartDate(startDate);
		criteria.setEndDate(endDate);
		criteria.setMinTotalAmount(minTotalAmount);
		criteria.setMaxTotalAmount(maxTotalAmount);

		Page<OrderResponse> orders = orderService.searchOrders(criteria, page, size, sortBy, sortDir);

		return ResponseEntity.ok(orders);
	}

	@PutMapping("/{id}/refund")
	public ResponseEntity<OrderResponse> refundOrder(@PathVariable Long id)
	{
//		// Check if current user is admin
//		if (!authService.isAdmin()) {
//			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
//		}
		OrderResponse response = orderService.refundOrder(id);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}/recalculate")
	public ResponseEntity<OrderResponse> recalculateOrder(@PathVariable Long id)
	{
//		// Only admin can perform this
//		if (!authService.isAdmin()) {
//			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
//		}
		OrderResponse updatedOrder = orderService.recalculateOrder(id);
		return ResponseEntity.ok(updatedOrder);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteOrder(@PathVariable Long id)
	{
//		// Only admin can delete orders
//		if (!authService.isAdmin()) {
//			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
//		}
		return orderService.deleteOrder(id);
	}

	@PutMapping("/{id}/payment-status")
	public ResponseEntity<OrderPaymentStatusResponse> updatePaymentStatus(
			@PathVariable Long id,
			@Valid @RequestBody UpdatePaymentStatusRequest request)
	{

		OrderPaymentStatusResponse response = orderService.updatePaymentStatus(id, request);

		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}/shipping-status")
	public ResponseEntity<OrderShippingStatusResponse> updateShippingStatus(
			@PathVariable Long id,
			@Valid @RequestBody UpdateShippingStatusRequest request)
	{
		OrderShippingStatusResponse response = orderService.updateShippingStatus(id, request);

		return ResponseEntity.ok(response);
	}
}

