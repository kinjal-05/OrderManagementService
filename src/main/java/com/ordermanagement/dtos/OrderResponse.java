package com.ordermanagement.dtos;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@JsonPOJOBuilder
public class OrderResponse
{
	private Long orderId;
	private Long userId;
	private BigDecimal totalAmount;
	private String orderStatus;
	private String paymentStatus;
	private String shippingStatus;
	private LocalDateTime createdAt;
	private List<OrderItemResponse> items;

	@JsonPOJOBuilder
	public static class OrderItemResponse
	{
		private Long productId;
		private String productName;
		private Integer quantity;
		private BigDecimal price;
		private BigDecimal totalPrice;

		public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

		public BigDecimal getPrice() {
			return price;
		}

		public void setPrice(BigDecimal price) {
			this.price = price;
		}

		public BigDecimal getTotalPrice() {
			return totalPrice;
		}

		public void setTotalPrice(BigDecimal totalPrice) {
			this.totalPrice = totalPrice;
		}

		public OrderItemResponse(Long productId, String productName, Integer quantity, BigDecimal price, BigDecimal totalPrice) {
			this.productId = productId;
			this.productName = productName;
			this.quantity = quantity;
			this.price = price;
			this.totalPrice = totalPrice;
		}

		public OrderItemResponse() {
		}
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(String shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<OrderItemResponse> getItems() {
		return items;
	}

	public void setItems(List<OrderItemResponse> items) {
		this.items = items;
	}

	public OrderResponse(Long orderId, Long userId, BigDecimal totalAmount, String orderStatus, String paymentStatus, String shippingStatus, LocalDateTime createdAt, List<OrderItemResponse> items) {
		this.orderId = orderId;
		this.userId = userId;
		this.totalAmount = totalAmount;
		this.orderStatus = orderStatus;
		this.paymentStatus = paymentStatus;
		this.shippingStatus = shippingStatus;
		this.createdAt = createdAt;
		this.items = items;
	}

	public OrderResponse() {
	}
}