package org.example.campusmarket.entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Product {
    private Long id;
    private Long sellerId;
    @NotBlank(message="商品标题不能为空")
    private String title;
    private String description;
    @DecimalMin(value="0.01",message="商品价格必须大于0")
    @NotNull(message = "商品价格不能为空")
    private BigDecimal price;
    @NotBlank(message = "商品分类不能为空")
    private String category;
    private String cover;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
