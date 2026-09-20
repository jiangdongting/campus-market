package org.example.campusmarket.controller;

import jakarta.validation.Valid;
import org.example.campusmarket.common.Result;
import org.example.campusmarket.entity.Product;
import org.example.campusmarket.service.ProductService;
import org.example.campusmarket.vo.ProductDetailVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping("/products")
        public Result<Integer> createProduct(
                @Valid @RequestBody Product product,
                @RequestAttribute("userId")Long userId){
        return Result.success(productService.createProduct(product,userId));
        }
    @GetMapping("/products/{id}")
    public Result<Product> getProductById(@PathVariable Long id){
        return Result.success(productService.getProductById(id));

    }
    @GetMapping("/products")
    public Result<List<Product>> getAllProducts(){
        return Result.success(productService.getAllProducts());
    }
    @PutMapping("/products/{id}")
    public Result<Integer> updateProduct(
            @PathVariable Long id,
            @Valid@RequestBody Product product,
            @RequestAttribute("userId") Long userId){
        return Result.success(productService.updateProduct(id,product,userId));


    }
    @PutMapping("/products/{id}/offline")
    public Result<Integer> offlineProduct(
            @PathVariable Long id,
            @RequestAttribute("userId") Long userId){
        return Result.success(productService.offlineProduct(id,userId));
    }
    @GetMapping("/products/search")
    public Result<List<Product>>searchProducts(@RequestParam String keyword){
        return Result.success(productService.searchProducts(keyword));
    }
    @GetMapping("/products/page")
    public Result<List<Product>> getProductPage(
            @RequestParam Integer page,
            @RequestParam Integer size) {

        return Result.success(productService.getProductPage(page, size));
    }
    @GetMapping("/products/me")
    public Result<List<Product>> getMyProducts(
            @RequestAttribute("userId")Long sellerId){
        return Result.success(productService.getMyProducts(sellerId));
    }
    @GetMapping("/products/{id}/detail")
    public Result<ProductDetailVO>
    getProductDetail(
            @PathVariable Long id,
            @RequestAttribute("userId")Long userId
    ){
        return Result.success(productService.getProductDetail(id,userId));
    }

    }







