package org.example.campusmarket.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDetailVO {

    // 商品ID
    private Long id;

    // 商品标题
    private String title;

    // 商品描述
    private String description;

    // 价格
    private BigDecimal price;

    // 分类
    private String category;

    // 图片
    private String cover;


    // 卖家ID
    private Long sellerId;

    // 卖家昵称
    private String sellerName;


    // 当前用户是否收藏
    private Boolean favorite;

    // 收藏数量
    private Integer favoriteCount;

}