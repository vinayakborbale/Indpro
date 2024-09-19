package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
	
    List<Orders> findByUserUserName(String username);
    Optional<Orders> findByIdAndUser_UserName(Long id, String username);
}
