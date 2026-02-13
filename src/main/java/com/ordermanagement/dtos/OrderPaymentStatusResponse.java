package com.ordermanagement.dtos;

import com.ordermanagement.models.PaymentStatus;

import java.time.LocalDateTime;

public class OrderPaymentStatusResponse
{
	private Long orderId;
	private PaymentStatus paymentStatus;
	private String message;
	private LocalDateTime updatedAt;

	public OrderPaymentStatusResponse(Long orderId, PaymentStatus paymentStatus, String message, LocalDateTime updatedAt) {
		this.orderId = orderId;
		this.paymentStatus = paymentStatus;
		this.message = message;
		this.updatedAt = updatedAt;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
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

	public OrderPaymentStatusResponse() {
	}
}