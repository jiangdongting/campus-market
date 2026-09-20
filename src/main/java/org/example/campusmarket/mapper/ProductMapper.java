package org.example.campusmarket.mapper;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.*;
import org.example.campusmarket.entity.Product;
import org.example.campusmarket.vo.ProductDetailVO;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Select("SELECT *FROM product WHERE id = #{id}")
    Product findById(Long id);

    @Options(
            useGeneratedKeys = true,
            keyProperty = "id"
    )
    @Insert("""
            INSERT INTO product
            (seller_id,title,description,price,category,cover)
            VALUES
            (#{sellerId}, #{title}, #{description}, #{price}, #{category}, #{cover})
            """)
    int insert(Product product);

    @Select("SELECT * FROM product WHERE status =1 ORDER BY created_at DESC")
    List<Product> findAll();

    @Update("""
            UPDATE product
            SET title = #{title},
                description = #{description},
                price = #{price},
                category = #{category},
                cover = #{cover}
            WHERE id = #{id}
            """)
    int update(Product product);

    @Select("""
            SELECT * FROM product
            WHERE seller_id =#{sellerId}
            ORDER BY created_at DESC""")
    List<Product> findBySellerId(
            @Param("sellerId") Long sellerId
    );

    @Update("UPDATE product SET status = 0 WHERE id = #{id}")
    int offline(Long id);

    @Select("""
            SELECT * FROM product
            WHERE status = 1
            AND title LIKE CONCAT('%', #{keyword}, '%')
            ORDER BY created_at DESC
            """)
    List<Product> search(String keyword);

    @Select("""
            SELECT * FROM product
            WHERE status = 1
            ORDER BY created_at DESC
            LIMIT #{size} OFFSET #{offset}
            """)
    List<Product> findPage(Integer size, Integer offset);

    @Update("""
            UPDATE product
            SET status=0
            WHERE id=#{productId}
            """)
    int updateStatus(@Param("productId") Long productId);

    @Select("""
        SELECT
            p.id,
            p.title,
            p.description,
            p.price,
            p.category,
            p.cover,
            p.seller_id,
            u.username AS seller_name,

            COUNT(f.id) AS favorite_count,

            CASE
                WHEN SUM(CASE WHEN f.user_id = #{userId} THEN 1 ELSE 0 END) > 0
                THEN TRUE
                ELSE FALSE
            END AS favorite

        FROM product p

        LEFT JOIN user u
        ON p.seller_id = u.id

        LEFT JOIN favorite f
        ON p.id = f.product_id

        WHERE p.id = #{id}

        GROUP BY
            p.id,
            p.title,
            p.description,
            p.price,
            p.category,
            p.cover,
            p.seller_id,
            u.username
        """)
    ProductDetailVO findDetailById(
            @Param("id") Long id,
            @Param("userId") Long userId
    );

}
