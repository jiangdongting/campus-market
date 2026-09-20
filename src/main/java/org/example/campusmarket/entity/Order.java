package org.example.campusmarket.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order {
    private Long id;
    private Long buyerId;
    private Long sellerId;
    private Long productId;
    private BigDecimal price;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
