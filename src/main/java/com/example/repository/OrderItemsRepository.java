package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.OrderItems;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
	
    List<OrderItems> findByOrderId(Long orderId);

	
	
}

