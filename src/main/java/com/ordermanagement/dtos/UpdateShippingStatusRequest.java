package com.ordermanagement.dtos;

import com.ordermanagement.models.ShippingStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateShippingStatusRequest
{
	@NotNull(message = "Shipping status is required")
	private ShippingStatus shippingStatus;

	private String remarks;

	public ShippingStatus getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(ShippingStatus shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}