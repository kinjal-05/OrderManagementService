package com.ordermanagement.dtos;

import com.ordermanagement.models.ShippingStatus;

import java.time.LocalDateTime;

public class OrderShippingStatusResponse
{
	private Long orderId;
	private ShippingStatus shippingStatus;
	private String message;
	private LocalDateTime updatedAt;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public ShippingStatus getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(ShippingStatus shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public OrderShippingStatusResponse(Long orderId, ShippingStatus shippingStatus, String message, LocalDateTime updatedAt) {
		this.orderId = orderId;
		this.shippingStatus = shippingStatus;
		this.message = message;
		this.updatedAt = updatedAt;
	}

	public OrderShippingStatusResponse() {
	}
}