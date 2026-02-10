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
		@NotNull(message = "Product Id is required")
		private Long id;

		@NotNull(message = "Quantity is required")
		@Min(value = 1,message = "Quantity must be atleast 1")
		private Integer quantity;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

		public OrderItemRequest(Long id, Integer quantity) {
			this.id = id;
			this.quantity = quantity;
		}

		public OrderItemRequest() {
		}
	}



}