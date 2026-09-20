# 校园二手交易平台

基于 Spring Boot 开发的校园二手交易后端项目，面向校园闲置物品交易场景。

## 技术栈

- Java 17
- Spring Boot
- MyBatis
- MySQL
- Redis
- JWT
- Maven

## 核心功能

- 用户注册、登录
- JWT 登录鉴权
- 商品发布、修改、下架
- 商品搜索、分页查询
- 我的发布
- 收藏、取消收藏、收藏列表
- 商品详情与卖家信息
- 创建订单
- 买家订单、卖家订单
- 订单详情
- 订单取消、完成
- Redis 商品详情缓存
- 统一返回结果与全局异常处理
## 项目亮点

- 使用 JWT 实现登录鉴权，并通过拦截器统一校验用户身份
- 使用 Redis 缓存商品详情，减少数据库重复查询
- 使用 MyBatis 完成商品、收藏、订单等业务数据访问
- 使用 VO 组合商品、卖家、收藏数量等多表信息
- 使用统一 Result 响应格式，规范接口返回结构
- 使用 GlobalExceptionHandler 统一处理业务异常和参数校验异常
- 使用事务保证订单完成与商品下架操作的一致性
- 数据库密码和 JWT 密钥使用环境变量管理，避免敏感信息直接写入代码

## 项目结构

```text
src/main/java/org/example/campusmarket
├── controller      接收前端请求
├── service         处理业务逻辑
├── mapper          操作 MySQL 数据库
├── entity          数据库实体类
├── vo              前端展示对象
├── interceptor     JWT 登录拦截器
├── config          Spring 配置
├── exception       全局异常处理
├── util            JWT、Redis 等工具类
└── common          Result 统一返回结果
## 核心接口

### 用户模块

| 请求方式 | 接口 | 说明 |
|---|---|---|
| POST | /users/register | 用户注册 |
| POST | /users/login | 用户登录 |
| GET | /users/me | 获取当前登录用户信息 |

### 商品模块

| 请求方式 | 接口 | 说明 |
|---|---|---|
| POST | /products | 发布商品 |
| GET | /products | 商品列表 |
| GET | /products/{id} | 商品基础详情 |
| GET | /products/{id}/detail | 商品完整详情 |
| PUT | /products/{id} | 修改商品 |
| PUT | /products/{id}/offline | 商品下架 |
| GET | /products/search | 搜索商品 |
| GET | /products/page | 分页查询商品 |
| GET | /products/me | 我的发布 |

### 收藏模块

| 请求方式 | 接口 | 说明 |
|---|---|---|
| POST | /favorites/{productId} | 收藏商品 |
| DELETE | /favorites/{productId} | 取消收藏 |
| GET | /favorites/me | 我的收藏 |

### 订单模块

| 请求方式 | 接口 | 说明 |
|---|---|---|
| POST | /orders/{productId} | 创建订单 |
| GET | /orders/me | 我的买家订单 |
| GET | /orders/seller/me | 我的卖家订单 |
| GET | /orders/{orderId} | 订单详情 |
| PUT | /orders/{orderId}/cancel | 取消订单 |
| PUT | /orders/{orderId}/complete | 完成订单 |
## 项目运行

### 1. 环境要求

- JDK 17
- MySQL
- Redis
- Maven

### 2. 创建数据库

创建数据库：

```sql
CREATE DATABASE campus_market;
## 数据库表说明

### user 用户表

主要字段：

- id：用户ID
- username：用户名
- password：加密后的密码
- nickname：昵称
- phone：手机号
- avatar：头像
- status：账号状态

### product 商品表

主要字段：

- id：商品ID
- seller_id：卖家用户ID
- title：商品标题
- description：商品描述
- price：商品价格
- category：商品分类
- cover：商品封面
- status：商品状态，1为正常，0为下架或已售

### favorite 收藏表

主要字段：

- id：收藏ID
- user_id：用户ID
- product_id：商品ID
- created_at：收藏时间

并通过唯一约束避免同一个用户重复收藏同一个商品。

### orders 订单表

主要字段：

- id：订单ID
- buyer_id：买家用户ID
- seller_id：卖家用户ID
- product_id：商品ID
- price：下单时商品价格
- status：订单状态
- created_at：创建时间
- updated_at：更新时间

订单状态：

- 0：待完成
- 1：已完成
- 2：已取消