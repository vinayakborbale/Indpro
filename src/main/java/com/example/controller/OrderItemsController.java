package com.example.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.OrderItemDTO;
import com.example.entities.OrderItems;
import com.example.service.OrderItemService;

@RestController
@RequestMapping("/orderItem")
public class OrderItemsController {

	@Autowired
	OrderItemService orderItemService;

	@GetMapping("/{id}")
	public ResponseEntity<List<OrderItemDTO>> getOrderItemById(@PathVariable Long id) {
	    List<OrderItems> listOfOrderItem = orderItemService.getOrderByOrderId(id);
	    if (listOfOrderItem != null && listOfOrderItem.size() > 0) {
	        List<OrderItemDTO> dtoList = new ArrayList<>();
	        for (OrderItems orderItem : listOfOrderItem) {
	            BigDecimal totalPrice = orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
	            OrderItemDTO dto = new OrderItemDTO(
	                    orderItem.getProduct().getId(), 
	                    orderItem.getProduct().getName(),
	                    orderItem.getQuantity(),
	                    orderItem.getPrice(),
	                    totalPrice
	            );
	            dtoList.add(dto);
	        }
	        return new ResponseEntity<>(dtoList, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
}
