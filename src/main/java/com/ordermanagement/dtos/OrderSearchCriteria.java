package com.ordermanagement.dtos;

import com.ordermanagement.models.OrderStatus;
import com.ordermanagement.models.PaymentStatus;
import com.ordermanagement.models.ShippingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderSearchCriteria
{
	private Long userId;
	private OrderStatus orderStatus;
	private PaymentStatus paymentStatus;
	private ShippingStatus shippingStatus;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private BigDecimal minTotalAmount;
	private BigDecimal maxTotalAmount;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public ShippingStatus getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(ShippingStatus shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getMinTotalAmount() {
		return minTotalAmount;
	}

	public void setMinTotalAmount(BigDecimal minTotalAmount) {
		this.minTotalAmount = minTotalAmount;
	}

	public BigDecimal getMaxTotalAmount() {
		return maxTotalAmount;
	}

	public void setMaxTotalAmount(BigDecimal maxTotalAmount) {
		this.maxTotalAmount = maxTotalAmount;
	}

	public OrderSearchCriteria(OrderStatus orderStatus, PaymentStatus paymentStatus, ShippingStatus shippingStatus, LocalDateTime startDate, LocalDateTime endDate, BigDecimal minTotalAmount, BigDecimal maxTotalAmount, Long userId) {
		this.orderStatus = orderStatus;
		this.paymentStatus = paymentStatus;
		this.shippingStatus = shippingStatus;
		this.startDate = startDate;
		this.endDate = endDate;
		this.minTotalAmount = minTotalAmount;
		this.maxTotalAmount = maxTotalAmount;
		this.userId = userId;
	}

	public OrderSearchCriteria() {
	}
}