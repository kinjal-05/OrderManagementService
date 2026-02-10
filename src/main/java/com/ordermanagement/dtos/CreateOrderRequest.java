package com.ordermanagement.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CreateOrderRequest
{

	@NotEmpty(message = "Order must contain atleast one item")
	private List<OrderItemRequest>items;

	public static class OrderItemRequest
	{
		@NotNull(message = "Product ID is required")
		private Long productId;

		@NotNull(message = "Quantity is required")
		@Min(value = 1,message = "Quantity must be atleast 1")
		private Integer quantity;


		public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

		public OrderItemRequest(Long productId, Integer quantity) {
			this.productId = productId;
			this.quantity = quantity;
		}

		public OrderItemRequest() {
		}
	}

	public List<OrderItemRequest> getItems() {
		return items;
	}

	public void setItems(List<OrderItemRequest> items) {
		this.items = items;
	}

	public CreateOrderRequest(List<OrderItemRequest> items) {
		this.items = items;
	}

	public CreateOrderRequest() {
	}
}