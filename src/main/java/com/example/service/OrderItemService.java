package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entities.OrderItems;
import com.example.repository.OrderItemsRepository;

@Service
public class OrderItemService {

	@Autowired
	OrderItemsRepository orderItemsRepository;

	public List<OrderItems> getOrderByOrderId(Long id) {
		List<OrderItems> orderItemList = orderItemsRepository.findByOrderId(id);
		return orderItemList;
	}

}
