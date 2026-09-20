package org.example.campusmarket.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetailVO {

    private Long id;

    private Long buyerId;

    private Long sellerId;

    private Long productId;

    private String productTitle;

    private String cover;

    private BigDecimal price;

    private Integer status;
}