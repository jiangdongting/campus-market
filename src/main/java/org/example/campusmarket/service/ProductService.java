package org.example.campusmarket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.campusmarket.entity.Product;
import org.example.campusmarket.exception.BusinessException;
import org.example.campusmarket.mapper.ProductMapper;
import org.example.campusmarket.util.RedisKey;
import org.example.campusmarket.vo.ProductDetailVO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.example.campusmarket.vo.ProductDetailVO;

@Service
public class ProductService {

    private final ProductMapper productMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;
    public ProductService(ProductMapper productMapper,
    StringRedisTemplate stringRedisTemplate,ObjectMapper objectMapper
                          ) {
        this.productMapper = productMapper;
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
    }

    public int createProduct(Product product,Long sellerId){
        product.setSellerId(sellerId);
        int result= productMapper.insert(product);
        if (result>0){

            System.out.println("新增商品ID：" + product.getId());

            String key = RedisKey.product(product.getId());

            try {

                String json = objectMapper.writeValueAsString(product);

                stringRedisTemplate.opsForValue()
                        .set(key,json,10,TimeUnit.MINUTES);

            }catch(Exception e){

                e.printStackTrace();

            }
        }

    return result;}
    public Product getProductById(Long id){

        String key =RedisKey.product(id);
        String json =stringRedisTemplate.opsForValue().get(key);
        if (json!=null) {
            try {
                return objectMapper.readValue(json, Product.class);
            } catch (Exception e) {
                stringRedisTemplate.delete(key);
            }
        }
            Product product=productMapper.findById(id);
        if (product!=null){
            try{
                String productJson =objectMapper.writeValueAsString(product);
                stringRedisTemplate.opsForValue().set(key,productJson,10, TimeUnit.MINUTES);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
                    return product;
        }

    public List<Product> getAllProducts() {
        return productMapper.findAll();
    }
    public int updateProduct(Long id,Product product,Long userId){
        Product dbProduct =productMapper.findById(id);
        if (dbProduct==null){
            return 0;
        }
        if (!dbProduct.getSellerId().equals(userId)){
            return -1;
        }
        product.setId(id);
        int result=productMapper.update(product);
        if (result>0){
            String key =RedisKey.product(id);
            stringRedisTemplate.delete(key);
        }       return result;
    }
    public int offlineProduct(Long id,Long userId){
        Product dbProduct =productMapper.findById(id);
        if (dbProduct==null){
            return 0;
        }
        if(!dbProduct.getSellerId().equals(userId)){
            return -1;

        }
        int result =productMapper.offline(id);
        if (result>0){
            String key = RedisKey.product(id);
            stringRedisTemplate.delete(key);
        }
        return result;
    }
    public List<Product> searchProducts(String keyword){
        return productMapper.search(keyword);
    }
    public List<Product> getProductPage(Integer page, Integer size){
        Integer offset = (page - 1) * size;
        return productMapper.findPage(size, offset);
    }
    public List<Product>getMyProducts(Long sellerId){
        return productMapper.findBySellerId(sellerId);
    }
    public ProductDetailVO getProductDetail(Long id,Long userId){
        ProductDetailVO detail=productMapper.findDetailById(id,userId);
        if(detail ==null){
            throw new BusinessException("商品不存在");
        }
        return detail;
    }

}