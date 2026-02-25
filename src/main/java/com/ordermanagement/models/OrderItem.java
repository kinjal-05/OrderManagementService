package com.ordermanagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Order Reference is required")
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name="order_id",nullable = false)
	private Order order;

	@NotNull(message = "Product ID is required")
	@Column(name="product_id",nullable = false)
	private Long productId;

	@NotBlank(message = "Product name is required")
	@Size(max=255,message = "Product name can not exceed 255 characters")
	@Column(name="product_name",nullable = false,length = 255)
	private String productName;

	@NotNull(message = "Quantity is Required")
	@Min(value = 1,message = "Quantity must be atleast one")
	@Column(name="quantity",nullable = false)
	private Integer quantity;

	@NotNull(message = "Price is required")
	@DecimalMin(value = "0.0",inclusive = false,message = "Price must be greater than zero")
	@Digits(integer = 8,fraction = 2,message = "Price must be valid currency format")
	@Column(name = "price",nullable = false,scale = 2)
	private BigDecimal price;

	@NotNull(message = "Total Price is Required")
	@DecimalMin(value="0.0",inclusive = false,message = "Total price must be greater than zero")
	@Digits(integer = 8,fraction = 2,message = "Total price must be in valid currency format")
	@Column(name = "total_price",nullable = false,precision = 10,scale = 2)
	private BigDecimal totalPrice;

	@PrePersist
	@PreUpdate
	public void calculateTotalPrice()
	{
		if(price!=null && quantity!=null)
		{
			this.totalPrice=price.multiply(BigDecimal.valueOf(quantity));
		}
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

	public OrderItem(Long id, Order order, Long productId, String productName, Integer quantity, BigDecimal price, BigDecimal totalPrice) {
		this.id = id;
		this.order = order;
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
		this.totalPrice = totalPrice;
	}

	public OrderItem() {
	}

	@Override
	public String toString() {
		return "OrderItem{" +
				"id=" + id +
				", order=" + order +
				", productId=" + productId +
				", productName='" + productName + '\'' +
				", quantity=" + quantity +
				", price=" + price +
				", totalPrice=" + totalPrice +
				'}';
	}
}