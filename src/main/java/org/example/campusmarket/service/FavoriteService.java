package org.example.campusmarket.service;


import org.example.campusmarket.entity.Favorite;
import org.example.campusmarket.exception.BusinessException;
import org.example.campusmarket.mapper.FavoriteMapper;
import org.example.campusmarket.vo.FavoriteVO;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.DuplicateFormatFlagsException;
import java.util.List;

@Service
public class FavoriteService {
    private final FavoriteMapper favoriteMapper;
    public FavoriteService(FavoriteMapper favoriteMapper){
        this.favoriteMapper=favoriteMapper;
    }
     public int addFavorite(Long userId,Long productId){
         Favorite favorite =new Favorite();
         favorite.setUserId(userId);
         favorite.setProductId(productId);
         try{
             return favoriteMapper.insert(favorite);}
             catch
             (DuplicateKeyException e) {throw new
                     BusinessException("该商品已收藏");
             }

     }
     public int removeFavorite(Long user,Long productId){
        return favoriteMapper.delete(user, productId);
     }
     public List<FavoriteVO> getMyFavorites(Long userId){
        return favoriteMapper.findFavoriteVOByUserId(userId);
     }

}