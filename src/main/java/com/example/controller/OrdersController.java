package com.example.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.OrderItemRequest;
import com.example.dto.OrderResponseDTO;
import com.example.entities.Orders;
import com.example.entities.User;
import com.example.service.OrdersService;

@RestController
@RequestMapping("/orders")
public class OrdersController {

	@Autowired
	private OrdersService ordersService;

	@PostMapping
	public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody List<OrderItemRequest> orderItems) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		// Process the order creation
		Orders newOrder = ordersService.createOrder(user.getUserName(), orderItems);
		OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
		orderResponseDTO.setUserName(user.getUserName());
		orderResponseDTO.setOrderId(newOrder.getId());
		orderResponseDTO.setTotalPrice(newOrder.getTotalPrice());
		orderResponseDTO.setStatus(newOrder.getStatus());
		orderResponseDTO.setCreatedAt(newOrder.getCreatedAt());
		return new ResponseEntity<>(orderResponseDTO, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal(); 
		// Fetch orders for the authenticated user
		List<Orders> orders = ordersService.getOrdersByUsername(user.getUserName());
		List<OrderResponseDTO> orderResponseDTOs = orders.stream().map(order -> {
			OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
			orderResponseDTO.setUserName(user.getUserName());
			orderResponseDTO.setOrderId(order.getId());
			orderResponseDTO.setTotalPrice(order.getTotalPrice());
			orderResponseDTO.setStatus(order.getStatus());
			orderResponseDTO.setCreatedAt(order.getCreatedAt());
			return orderResponseDTO;
		}).collect(Collectors.toList());
		return new ResponseEntity<>(orderResponseDTOs, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal(); 
		// Fetch the order by ID
		Orders order = ordersService.getOrderByIdAndUsername(id, user.getUserName());
		if (order != null) {
			OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
			orderResponseDTO.setUserName(user.getUserName());
			orderResponseDTO.setOrderId(order.getId());
			orderResponseDTO.setTotalPrice(order.getTotalPrice());
			orderResponseDTO.setStatus(order.getStatus());
			orderResponseDTO.setCreatedAt(order.getCreatedAt());
			return new ResponseEntity<>(orderResponseDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
