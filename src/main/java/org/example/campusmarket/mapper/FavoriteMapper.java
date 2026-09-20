package org.example.campusmarket.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.campusmarket.entity.Favorite;
import org.example.campusmarket.vo.FavoriteVO;

import java.util.List;

@Mapper
public interface FavoriteMapper {

    @Insert("""
            INSERT INTO favorite(user_id, product_id)
            VALUES(#{userId}, #{productId})
            """)
    int insert(Favorite favorite);

    @Select("""
            SELECT 
                f.id AS favorite_id,
                f.product_id,
                f.created_at,
                p.title,
                p.price,
                p.cover,
                p.status
            FROM favorite f 
            left join product p 
            on f.product_id =p.id
            where f.user_id =#{userId}
            order by f.created_at desc
            
            """)
    List<FavoriteVO> findFavoriteVOByUserId(Long userId);
    @Delete("""
        DELETE FROM favorite
        WHERE user_id = #{userId}
        AND product_id = #{productId}
        """)
    int delete(Long userId, Long productId);
}