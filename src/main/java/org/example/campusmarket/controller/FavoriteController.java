package org.example.campusmarket.controller;
import org.example.campusmarket.common.Result;
import org.example.campusmarket.service.FavoriteService;
import org.example.campusmarket.vo.FavoriteVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }
    @PostMapping("/favorites/{productId}")
    public Result<Integer> addFavorite(
            @PathVariable Long productId,
            @RequestAttribute("userId")Long userId){
        return Result.success(favoriteService.addFavorite(userId,productId));
    }
    @GetMapping("/favorites/me")
    public Result<List<FavoriteVO>>getMyFavorites(
            @RequestAttribute("userId")Long userId){
        return Result.success(favoriteService.getMyFavorites(userId)
        );
    }
    @DeleteMapping("/favorites/{productId}")
    public Result<Integer> removeFavorite(
            @PathVariable Long productId,
            @RequestAttribute("userId")Long userId){
                return Result.success(favoriteService.removeFavorite(userId, productId));
    }

}
