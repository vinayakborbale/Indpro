package com.example.dto;

import java.math.BigDecimal;

public class OrderItemDTO {
    private Long productId;
    private String productName;
    private Integer productQuantity;
    private BigDecimal price;
    private BigDecimal totalPrice;

    public OrderItemDTO() {}

    public OrderItemDTO(Long productId, String productName, Integer productQuantity, BigDecimal price, BigDecimal totalPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}