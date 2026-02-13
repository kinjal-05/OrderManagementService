package com.ordermanagement.dtos;

import java.time.LocalDateTime;

public class OrderStatusHistoryResponse
{
	private String orderStatus;
	private String remarks;
	private LocalDateTime createdAt;

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public OrderStatusHistoryResponse(String orderStatus, String remarks, LocalDateTime createdAt) {
		this.orderStatus = orderStatus;
		this.remarks = remarks;
		this.createdAt = createdAt;
	}

	public OrderStatusHistoryResponse() {
	}
}