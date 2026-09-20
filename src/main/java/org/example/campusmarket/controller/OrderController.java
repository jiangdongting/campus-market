package org.example.campusmarket.controller;

import org.example.campusmarket.common.Result;
import org.example.campusmarket.entity.Order;
import org.example.campusmarket.service.OrderService;
import org.example.campusmarket.vo.OrderDetailVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders/{productId}")
    public Result<?> createdOrder(
            @PathVariable Long productId,
            @RequestAttribute("userId") Long buyerId) {

        orderService.createOrder(buyerId, productId);

        return Result.success(null);
    }

    @GetMapping("/orders/me")
    public Result<List<Order>> getMyOrders(
            @RequestAttribute("userId") Long buyerId) {
        List<Order> orders=orderService.getMyOrders(buyerId);
        return Result.success(orders);

    }
    @GetMapping("/orders/{orderId}")
    public Result<OrderDetailVO> getOrderDetail(
            @PathVariable Long orderId,
            @RequestAttribute("userId")Long userId){
        OrderDetailVO orderDetail =orderService.getOrderDetail(orderId,userId);
        return Result.success(orderDetail);
    }
    @GetMapping("/orders/seller/me")
    public Result<List<Order>>getMySellerOrders(
            @RequestAttribute("userId")Long sellerId){
        List<Order>orders=orderService.getMySellerOrder(sellerId);
        return Result.success(orders);
    }


    @PutMapping("/orders/{orderId}/cancel")
    public Result<?> cancelOrder(
            @PathVariable Long orderId,
            @RequestAttribute("userId") Long buyerId) {
        orderService.cancelOrder(orderId,buyerId);
        return Result.success(null);
    }

    @PutMapping("/orders/{orderId}/complete")
    public Result<?> completeOrder(
            @PathVariable Long orderId,
            @RequestAttribute("userId") Long buyerId) {
        orderService.completeOrder(orderId,buyerId);
        return Result.success(null);


    }
}