package org.example.campusmarket.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FavoriteVO {

    private Long favoriteId;
    private Long productId;

    private String title;
    private BigDecimal price;
    private String cover;
    private Integer status;

    private LocalDateTime createdAt;
}