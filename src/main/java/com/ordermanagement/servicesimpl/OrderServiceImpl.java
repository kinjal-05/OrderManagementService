package com.ordermanagement.servicesimpl;


import com.ordermanagement.dtos.CreateOrderRequest;
import com.ordermanagement.dtos.OrderResponse;
import com.ordermanagement.models.*;
import com.ordermanagement.repositories.OrderItemRepository;
import com.ordermanagement.repositories.OrderRepository;
import com.ordermanagement.repositories.OrderStatusHistoryRepository;
import com.ordermanagement.services.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
}
