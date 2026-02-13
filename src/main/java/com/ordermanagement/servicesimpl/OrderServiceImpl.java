package com.ordermanagement.servicesimpl;


import com.ordermanagement.dtos.*;
import com.ordermanagement.models.*;
import com.ordermanagement.repositories.OrderItemRepository;
import com.ordermanagement.repositories.OrderRepository;
import com.ordermanagement.repositories.OrderStatusHistoryRepository;
import com.ordermanagement.services.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final OrderStatusHistoryRepository historyRepository;

	public OrderServiceImpl(OrderRepository orderRepository,
	                        OrderItemRepository orderItemRepository,
	                        OrderStatusHistoryRepository historyRepository) {
		this.orderRepository = orderRepository;
		this.orderItemRepository = orderItemRepository;
		this.historyRepository = historyRepository;
	}

	@Override
	public OrderResponse createOrder(Long userId, CreateOrderRequest request) {

		Order savedOrder = new Order();
		savedOrder.setUserId(userId);
		savedOrder.setOrderStatus(OrderStatus.CREATED);
		savedOrder.setPaymentStatus(PaymentStatus.PENDING);
		savedOrder.setShippingStatus(ShippingStatus.NOT_SHIPPED);

		BigDecimal totalAmount = BigDecimal.ZERO;
		List<OrderItem> savedItems = new ArrayList<>();

		for (CreateOrderRequest.OrderItemRequest itemReq : request.getItems()) {
			BigDecimal price = fetchProductPrice(itemReq.getProductId());
			String productName = fetchProductName(itemReq.getProductId());

			OrderItem item = new OrderItem();
			item.setOrder(savedOrder);
			item.setProductId(itemReq.getProductId());
			item.setProductName(productName);
			item.setQuantity(itemReq.getQuantity());
			item.setPrice(price);
			item.setTotalPrice(price.multiply(BigDecimal.valueOf(itemReq.getQuantity())));

			savedItems.add(item);
			totalAmount = totalAmount.add(item.getTotalPrice());
		}

		savedOrder.setTotalAmount(totalAmount);
		savedOrder.setItems(savedItems);

		orderRepository.save(savedOrder);

		OrderStatusHistory history = new OrderStatusHistory();
		history.setOrder(savedOrder);
		history.setOrderStatus(OrderStatus.CREATED);
		history.setRemarks("Order placed");
		historyRepository.save(history);

		return mapToResponse(savedOrder, savedItems);

	}


	private BigDecimal fetchProductPrice(Long productId) {
		return BigDecimal.valueOf(500);
	}

	private String fetchProductName(Long productId) {
		return "Sample Product";
	}

	private OrderResponse mapToResponse(Order order, List<OrderItem> items) {

		OrderResponse response = new OrderResponse();
		response.setOrderId(order.getId());
		response.setUserId(order.getUserId());
		response.setTotalAmount(order.getTotalAmount());
		response.setOrderStatus(order.getOrderStatus().name());
		response.setPaymentStatus(order.getPaymentStatus().name());
		response.setShippingStatus(order.getShippingStatus().name());
		response.setCreatedAt(order.getCreatedAt());

		List<OrderResponse.OrderItemResponse> itemResponses = new ArrayList<>();

		for (OrderItem item : items) {
			OrderResponse.OrderItemResponse itemResponse = new OrderResponse.OrderItemResponse();
			itemResponse.setProductId(item.getProductId());
			itemResponse.setProductName(item.getProductName());
			itemResponse.setQuantity(item.getQuantity());
			itemResponse.setPrice(item.getPrice());
			itemResponse.setTotalPrice(item.getTotalPrice());

			itemResponses.add(itemResponse);
		}

		response.setItems(itemResponses);

		return response;
	}

	@Override
	public OrderResponse getOrderById(Long userId, Long orderId) {

		Order order = orderRepository.findByIdAndUserId(orderId, userId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));


		List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());


		return mapToResponse(order, items);
	}

	@Override
	public Page<OrderResponse> getOrdersForCurrentUser(int page, int size)
	{
		// TODO: Replace with authService.getCurrentUserId();
		Long currentUserId = 1L;

		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		Page<Order> ordersPage = orderRepository.findAllByUserId(currentUserId, pageable);

		List<OrderResponse> responses = ordersPage.stream()
				.map(order -> {
					List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());
					return mapToResponse(order, items);
				})
				.toList();

		return new PageImpl<>(responses, pageable, ordersPage.getTotalElements());
	}

	@Override
	public OrderResponse cancelOrderForCurrentUser(Long orderId)
	{
//		Long currentUserId = authService.getCurrentUserId();
		Long currentUserId=1L;
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

		if (!order.getUserId().equals(currentUserId))
		{
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
		}

		if (order.getOrderStatus() == OrderStatus.CANCELLED || order.getOrderStatus() == OrderStatus.CANCELLED)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order cannot be cancelled at this stage");
		}

		order.setOrderStatus(OrderStatus.CANCELLED);
		orderRepository.save(order);


		OrderStatusHistory history = new OrderStatusHistory();
		history.setOrder(order);
		history.setOrderStatus(OrderStatus.CANCELLED);
		history.setRemarks("Order cancelled by user");
		historyRepository.save(history);

		List<OrderItem> items = orderItemRepository.findByOrderId(orderId);
		return mapToResponse(order, items);
	}

	@Override
	public List<OrderStatusHistoryResponse> getOrderHistoryForCurrentUser(Long orderId)
	{
//		Long currentUserId = authService.getCurrentUserId();
		Long currentUserId=1L;
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

		if (!order.getUserId().equals(currentUserId))
		{
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
		}

		List<OrderStatusHistory> historyList = historyRepository.findByOrderIdOrderByCreatedAtAsc(orderId);

		List<OrderStatusHistoryResponse> responses = new ArrayList<>();
		for (OrderStatusHistory history : historyList) {
			OrderStatusHistoryResponse resp = new OrderStatusHistoryResponse();
			resp.setOrderStatus(history.getOrderStatus().name());
			resp.setRemarks(history.getRemarks());
			resp.setCreatedAt(history.getChangedAt());
			responses.add(resp);
		}

		return responses;
	}


	@Override
	public Page<OrderResponse> getOrdersByUserId(Long userId, int page, int size)
	{
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		Page<Order> ordersPage = orderRepository.findAllByUserId(userId, pageable);

		List<OrderResponse> orderResponses = ordersPage.stream()
				.map(order -> {
					List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());
					return mapToResponse(order, items);
				})
				.toList();

		return new PageImpl<>(orderResponses, pageable, ordersPage.getTotalElements());
	}

	@Override
	public Page<OrderResponse> searchOrders(OrderSearchCriteria criteria, int page, int size, String sortBy, String sortDir) {

		Sort sort = Sort.by(sortBy);
		sort = sortDir.equalsIgnoreCase("desc") ? sort.descending() : sort.ascending();

		Pageable pageable = PageRequest.of(page, size, sort);

		Specification<Order> specification = new OrderSpecification(criteria);

		Page<Order> orderPage = orderRepository.findAll(specification, pageable);


		return orderPage.map(order -> mapToResponse(order, order.getItems()));
	}

	@Override
	public OrderResponse refundOrder(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

		order.setPaymentStatus(PaymentStatus.REFUNDED);
		order.setOrderStatus(OrderStatus.CANCELLED);
		orderRepository.save(order);

		OrderStatusHistory history = new OrderStatusHistory();
		history.setOrder(order);
		history.setOrderStatus(OrderStatus.CANCELLED);
		history.setRemarks("Order refunded by admin");
		historyRepository.save(history);

		return mapToResponse(order, order.getItems());
	}

	@Override
	@Transactional
	public OrderResponse recalculateOrder(Long orderId)
	{
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));


		BigDecimal totalAmount = order.getItems().stream()
				.map(OrderItem::getTotalPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		order.setTotalAmount(totalAmount);

		OrderStatusHistory history = new OrderStatusHistory();
		history.setOrder(order);
		history.setOrderStatus(order.getOrderStatus());
		history.setRemarks("Total amount recalculated by admin");
		order.getStatusHistoryList().add(history);

		orderRepository.save(order);


		return mapToResponse(order, order.getItems());
	}

	@Override
	@Transactional
	public ResponseEntity<String> deleteOrder(Long orderId)
	{
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
		orderRepository.delete(order);
		return ResponseEntity.ok("Order with ID " + orderId + " has been successfully deleted.");
	}

	@Override
	public OrderPaymentStatusResponse updatePaymentStatus(Long orderId, UpdatePaymentStatusRequest request)
	{

		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

		if (order.getPaymentStatus() == PaymentStatus.PAID)
		{
			throw new RuntimeException("Payment already completed for this order");
		}

		order.setPaymentStatus(request.getPaymentStatus());


		Order updatedOrder = orderRepository.save(order);

		return new OrderPaymentStatusResponse(
				updatedOrder.getId(),
				updatedOrder.getPaymentStatus(),
				"Payment status updated successfully",
				updatedOrder.getUpdatedAt()
		);
	}

	@Override
	public OrderShippingStatusResponse updateShippingStatus(Long orderId, UpdateShippingStatusRequest request) {

		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

		if (order.getPaymentStatus() != PaymentStatus.PAID) {
			throw new RuntimeException("Cannot update shipping. Payment is not completed.");
		}

		if (order.getShippingStatus() == ShippingStatus.DELIVERED) {
			throw new RuntimeException("Order already delivered. Status cannot be changed.");
		}

		order.setShippingStatus(request.getShippingStatus());

		if (request.getShippingStatus() == ShippingStatus.DELIVERED) {
			 order.setOrderStatus(OrderStatus.COMPLETED);
		}

		Order updatedOrder = orderRepository.save(order);

		return new OrderShippingStatusResponse(
				updatedOrder.getId(),
				updatedOrder.getShippingStatus(),
				"Shipping status updated successfully",
				updatedOrder.getUpdatedAt()
		);
	}
}
