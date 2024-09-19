package com.example.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.OrderItemRequest;
import com.example.entities.OrderItems;
import com.example.entities.Orders;
import com.example.entities.Product;
import com.example.entities.User;
import com.example.repository.OrderItemsRepository;
import com.example.repository.OrdersRepository;
import com.example.repository.ProductRepository;
import com.example.repository.UserRepository;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private ProductRepository productsRepository;

    // Create a new order for the authenticated user
    public Orders createOrder(String username, List<OrderItemRequest> orderItems) {
        // Find the user by username (fetched from JWT)
        User user = usersRepository.findByUserName(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Calculate total price and set order details
        BigDecimal totalPrice = calculateTotalPrice(orderItems);

        Orders newOrder = new Orders();
        newOrder.setUser(user);
        //newOrder.setUserName(user.getUserName());
        newOrder.setTotalPrice(totalPrice);
        newOrder.setStatus("Pending");
        newOrder.setCreatedAt(LocalDateTime.now());

        // Save the order
        Orders savedOrder = ordersRepository.save(newOrder);

        // Save each order item
        for (OrderItemRequest itemRequest : orderItems) {
            Optional<Product> product = productsRepository.findById(itemRequest.getProductId());
            if (product.isPresent()) {
                OrderItems orderItem = new OrderItems();
                orderItem.setOrder(savedOrder);
                orderItem.setProduct(product.get());
                orderItem.setQuantity(itemRequest.getQuantity());
                orderItem.setPrice(itemRequest.getPrice());

                orderItemsRepository.save(orderItem);
            } else {
                throw new RuntimeException("Product not found for ID: " + itemRequest.getProductId());
            }
        }

        return savedOrder;
    }

    // Get all orders for the authenticated user
    public List<Orders> getOrdersByUsername(String username) {
        return ordersRepository.findByUserUserName(username);
    }

    // Get specific order by ID and username to ensure the order belongs to the user
    public Orders getOrderByIdAndUsername(Long id, String username) {
        return ordersRepository.findByIdAndUser_UserName(id, username)
                .orElseThrow(() -> new RuntimeException("Order not found or does not belong to the user"));
    }

    // Calculate total price of all order items
    private BigDecimal calculateTotalPrice(List<OrderItemRequest> orderItems) {
        return orderItems.stream()
                         .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                         .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

