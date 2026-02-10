package com.ordermanagement.repositories;

import com.ordermanagement.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>
{
}