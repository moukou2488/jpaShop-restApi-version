package com.jpaproject.shop.controller.order;

import com.jpaproject.shop.domain.*;
import com.jpaproject.shop.domain.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderResponse {

    private Long orderId;

    private String name;

    private Address address;

    private LocalDateTime orderDate;

    private OrderStatus status; //주문상태 [ORDER,CANCLE]

    public OrderResponse(Order order) {
        this.name = order.getUser().getName(); // LAZY 초기화
        this.orderId = order.getId();
        this.address = order.getUser().getAddress(); // LAZY 초기화
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus();
    }

    // == custom dto ==

    @Data
    public static class ItemsOrderResponse {
        private Long orderId;

        private String name;

        private Address address;

        private LocalDateTime orderDate;

        private OrderStatus status; //주문상태 [ORDER,CANCLE]

        private List<ItemResponse> orderItems;

        public ItemsOrderResponse(Order order) {
            this.name = order.getUser().getName(); // LAZY 초기화
            this.orderId = order.getId();
            this.address = order.getUser().getAddress(); // LAZY 초기화
            this.orderDate = order.getOrderDate();
            this.status = order.getStatus();
            this.orderItems = order.getOrderItems().stream().
                    map(orderItem -> new ItemResponse(orderItem)).
                    collect(Collectors.toList());
        }
    }

    @Data
    public static class ItemResponse {
        private String itemName; //상품명
        private int orderPrice; //주문 가격
        private int count; //주문 수량

        public ItemResponse(OrderItem orderItem) {
            this.itemName = orderItem.getItem().getName();
            this.orderPrice = orderItem.getOrderPrice();
            this.count = orderItem.getCount();
        }
    }

}
