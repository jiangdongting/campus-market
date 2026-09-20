package org.example.campusmarket.service;

import org.example.campusmarket.entity.Order;
import org.example.campusmarket.entity.Product;
import org.example.campusmarket.exception.BusinessException;
import org.example.campusmarket.mapper.OrderMapper;
import org.example.campusmarket.mapper.ProductMapper;
import org.example.campusmarket.vo.OrderDetailVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class OrderService {

    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;

    public OrderService(OrderMapper orderMapper, ProductMapper productMapper) {
        this.orderMapper = orderMapper;
        this.productMapper = productMapper;
    }

    public int createOrder(Long buyerId, Long productId) {

        Product product = productMapper.findById(productId);

        if (product == null) {
            throw new BusinessException("商品不存在");
        }

        if (product.getSellerId().equals(buyerId)) {
            throw new BusinessException("不能购买自己的商品");
        }


        if (product.getStatus()!=1){
            throw new BusinessException("商品不可购买");
        }
        Order existingOrder = orderMapper.findPendingOrder(buyerId, productId);

        if (existingOrder != null) {
            throw new BusinessException("已有待交易订单，不能重复下单");
        }


        Order order = new Order();
        order.setBuyerId(buyerId);
        order.setSellerId(product.getSellerId());
        order.setProductId(productId);
        order.setPrice(product.getPrice());

        return orderMapper.insert(order);
    }
    public List<Order> getMyOrders(Long buyerId) {
        return orderMapper.findByBuyerId(buyerId);
    }
    public List<Order> getMySellerOrder(Long sellerId){
        return orderMapper.findBySellerId(sellerId);
    }

    public int cancelOrder(Long orderId, Long buyerId) {

        Order order = orderMapper.findById(orderId);

        if (order == null) {
            throw new BusinessException("订单不存在");
        }

        if (!order.getBuyerId().equals(buyerId)) {
            throw new BusinessException("不能操作别人的订单");
        }

        if (order.getStatus() != 0) {
            throw new BusinessException("当前订单状态不能取消");
        }

        return orderMapper.cancel(orderId);
    }
    @Transactional

    public  void  completeOrder(Long orderId, Long buyerId) {

        Order order = orderMapper.findById(orderId);

        if (order == null) {
            throw new BusinessException("订单不存在");
        }

        if (!order.getBuyerId().equals(buyerId)) {
            throw new BusinessException("不能操作别人的订单");
        }

        if (order.getStatus() != 0) {
            throw new BusinessException("当前订单状态不能完成");
        }
        orderMapper.complete(orderId);
        productMapper.updateStatus(order.getProductId());

    }
    public OrderDetailVO getOrderDetail(Long orderId, Long userId){
        Order order =orderMapper.findById(orderId);
        if (order==null){
            throw new BusinessException("订单不存在");
        }
        if (!order.getBuyerId().equals(userId)
        && !order.getSellerId().equals(userId)){
            throw new BusinessException("无权查看该订单");
        }
        Product product =productMapper.findById(order.getProductId());
        OrderDetailVO vo =new OrderDetailVO();
        vo.setId(order.getId());
        vo.setBuyerId(order.getBuyerId());
        vo.setSellerId(order.getSellerId());
        vo.setProductId(order.getProductId());
        vo.setPrice(order.getPrice());
        vo.setStatus(order.getStatus());
        if (product != null) {
            vo.setProductTitle(product.getTitle());
            vo.setCover(product.getCover());
        }
        return vo;


        }

    }