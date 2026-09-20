package org.example.campusmarket.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.campusmarket.entity.Order;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Insert("""
            INSERT INTO orders
            (buyer_id, seller_id, product_id, price)
            VALUES (#{buyerId}, #{sellerId}, #{productId}, #{price})
            """)
    int insert(Order order);

    @Select("""
            SELECT * FROM orders
            WHERE buyer_id = #{buyerId}
            ORDER BY created_at DESC
            """)
    List<Order> findByBuyerId(
            @Param("buyerId") Long buyerId
    );
    @Select("""
            SELECT * FROM orders
            WHERE id = #{orderId}
            """)
    Order findById(
            @Param("orderId") Long orderId
    );
    @Select("""
SELECT *FROM  orders WHERE seller_id=#{sellerId}
ORDER BY created_at DESC""")
    List<Order>findBySellerId(
            @Param("sellerId")Long sellerId
    );

    @Update("""
            UPDATE orders
            SET status = 2
            WHERE id = #{orderId}
            """)
    int cancel(
            @Param("orderId") Long orderId
    );

    @Update("""
            UPDATE orders
            SET status = 1
            WHERE id = #{orderId}
            """)
    int complete(
            @Param("orderId") Long orderId
    );

    @Select("""
            SELECT * FROM orders
            WHERE buyer_id = #{buyerId}
            AND product_id = #{productId}
            AND status = 0
            LIMIT 1
            """)
    Order findPendingOrder(
            @Param("buyerId") Long buyerId,
            @Param("productId") Long productId
    );
}