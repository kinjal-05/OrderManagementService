package com.ordermanagement.repositories;

import com.ordermanagement.models.Order;
import com.ordermanagement.models.OrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Long>
{
	List<OrderStatusHistory> findByOrderIdOrderByCreatedAtAsc(Long orderId);
}