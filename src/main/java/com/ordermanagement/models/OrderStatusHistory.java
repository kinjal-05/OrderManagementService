package com.ordermanagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_status_history")
public class OrderStatusHistory
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Order reference is required")
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name = "order_id",nullable = false)
	private Order order;

	@NotNull(message = "Order status is required")
	@Enumerated(EnumType.STRING)
	@Column(name="order_status",nullable = false,length = 30)
	private OrderStatus orderStatus;

	@NotNull(message = "Changed time is required")
	@Column(name = "changed_at",nullable = false,updatable = false)
	private LocalDateTime changedAt;

	@Size(max=255,message = "Remark can not exceed 255 characters")
	@Column(name = "remarks",length = 255)
	private String remarks;

	@PrePersist
	protected void onCreate()
	{
		this.changedAt=LocalDateTime.now();
	}

	private LocalDateTime createdAt;

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public LocalDateTime getChangedAt() {
		return changedAt;
	}

	public void setChangedAt(LocalDateTime changedAt) {
		this.changedAt = changedAt;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public OrderStatusHistory(Long id, Order order, OrderStatus orderStatus, LocalDateTime changedAt, String remarks) {
		this.id = id;
		this.order = order;
		this.orderStatus = orderStatus;
		this.changedAt = changedAt;
		this.remarks = remarks;
	}

	public OrderStatusHistory() {
	}

	@Override
	public String toString() {
		return "OrderStatusHistory{" +
				"id=" + id +
				", order=" + order +
				", orderStatus=" + orderStatus +
				", changedAt=" + changedAt +
				", remarks='" + remarks + '\'' +
				'}';
	}
}
