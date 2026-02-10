package com.ordermanagement.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
public class Order
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "User Id is Required")
	@Column(name="user_id",nullable = false)
	private Long userId;

	@NotNull(message = "Total amount is required")
	@DecimalMin(value = "0.0",inclusive = false,message = "Total amount must be greater than zero")
	@Digits(integer = 8,fraction = 2,message = "Total amount must be in valid currency")
	@Column(name="total_amount",nullable = false,precision = 10,scale = 2)
	private BigDecimal totalAmount;

	@NotNull(message = "Order Status is Required")
	@Enumerated(EnumType.STRING)
	@Column(name="order_status",nullable = false,length = 30)
	private OrderStatus orderStatus;

	@NotNull(message = "Payment Status is required")
	@Enumerated(EnumType.STRING)
	@Column(name="payment_status",nullable = false,length = 30)
	private PaymentStatus paymentStatus;

	@NotNull(message = "Shipping status is required")
	@Enumerated(EnumType.STRING)
	@Column(name="shipping_status",nullable = false,length = 30)
	private ShippingStatus shippingStatus;

	@Column(name="created_at",nullable = false,updatable = false)
	private LocalDateTime createdAt;

	@Column(name="updated_at")
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
	@OrderBy("changedAt ASC")
	private List<OrderStatusHistory> statusHistoryList;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> items = new ArrayList<>();

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	@PrePersist
	protected  void onCreate()
	{
		this.createdAt=LocalDateTime.now();
		this.updatedAt=LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate()
	{
		this.updatedAt=LocalDateTime.now();
	}

	public List<OrderStatusHistory> getStatusHistoryList() {
		return statusHistoryList;
	}

	public void setStatusHistoryList(List<OrderStatusHistory> statusHistoryList) {
		this.statusHistoryList = statusHistoryList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Order() {
	}

	public Order(Long id, Long userId, BigDecimal totalAmount, OrderStatus orderStatus, PaymentStatus paymentStatus, ShippingStatus shippingStatus, LocalDateTime createdAt, LocalDateTime updatedAt, List<OrderStatusHistory> statusHistoryList, List<OrderItem> items) {
		this.id = id;
		this.userId = userId;
		this.totalAmount = totalAmount;
		this.orderStatus = orderStatus;
		this.paymentStatus = paymentStatus;
		this.shippingStatus = shippingStatus;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.statusHistoryList = statusHistoryList;
		this.items = items;
	}

	@Override
	public String toString() {
		return "Order{" +
				"id=" + id +
				", userId=" + userId +
				", totalAmount=" + totalAmount +
				", orderStatus=" + orderStatus +
				", paymentStatus=" + paymentStatus +
				", shippingStatus=" + shippingStatus +
				", createdAt=" + createdAt +
				", updatedAt=" + updatedAt +
				", statusHistoryList=" + statusHistoryList +
				", items=" + items +
				'}';
	}

//	public void addItem(OrderItem item) {
//		items.add(item);
//		item.setOrder(this);
//	}
//
//	public void removeItem(OrderItem item) {
//		items.remove(item);
//		item.setOrder(null);
//	}
//
//	public void addStatusHistory(OrderStatus status, String remarks) {
//		OrderStatusHistory history = new OrderStatusHistory();
//		history.setOrder(this);
//		history.setOrderStatus(status);
//		history.setRemarks(remarks);
//
//		statusHistoryList.add(history);
//	}



}
