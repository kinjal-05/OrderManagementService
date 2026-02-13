package com.ordermanagement.repositories;

import com.ordermanagement.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order>
{
	Optional<Order> findByIdAndUserId(Long id, Long userId);
	Page<Order> findAllByUserId(Long userId, Pageable pageable);
}