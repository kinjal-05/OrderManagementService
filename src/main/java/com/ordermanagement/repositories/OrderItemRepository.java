package com.ordermanagement.repositories;

import com.ordermanagement.models.Order;
import com.ordermanagement.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>
{
}