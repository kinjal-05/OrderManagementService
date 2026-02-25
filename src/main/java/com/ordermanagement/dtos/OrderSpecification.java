package com.ordermanagement.dtos;

import com.ordermanagement.models.Order;
import com.ordermanagement.dtos.OrderSearchCriteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class OrderSpecification implements Specification<Order> {

	private final OrderSearchCriteria criteria;

	public OrderSpecification(OrderSearchCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<>();

		// Dynamic list of field + value + builder function
		List<BiFunction<Root<Order>, CriteriaBuilder, Predicate>> conditions = List.of(
				(r, b) -> criteria.getUserId() != null ? b.equal(r.get("userId"), criteria.getUserId()) : null,
				(r, b) -> criteria.getOrderStatus() != null ? b.equal(r.get("orderStatus"), criteria.getOrderStatus()) : null,
				(r, b) -> criteria.getPaymentStatus() != null ? b.equal(r.get("paymentStatus"), criteria.getPaymentStatus()) : null,
				(r, b) -> criteria.getShippingStatus() != null ? b.equal(r.get("shippingStatus"), criteria.getShippingStatus()) : null,
				(r, b) -> criteria.getStartDate() != null ? b.greaterThanOrEqualTo(r.get("createdAt"), criteria.getStartDate()) : null,
				(r, b) -> criteria.getEndDate() != null ? b.lessThanOrEqualTo(r.get("createdAt"), criteria.getEndDate()) : null,
				(r, b) -> criteria.getMinTotalAmount() != null ? b.greaterThanOrEqualTo(r.get("totalAmount"), criteria.getMinTotalAmount()) : null,
				(r, b) -> criteria.getMaxTotalAmount() != null ? b.lessThanOrEqualTo(r.get("totalAmount"), criteria.getMaxTotalAmount()) : null
		);

		// Build predicates dynamically
		conditions.stream()
				.map(f -> f.apply(root, cb))
				.filter(p -> p != null)
				.forEach(predicates::add);

		return cb.and(predicates.toArray(new Predicate[0]));
	}
}