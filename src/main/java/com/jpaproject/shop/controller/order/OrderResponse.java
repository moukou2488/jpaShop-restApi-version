package com.jpaproject.shop.controller.order;

import com.jpaproject.shop.domain.*;
import com.jpaproject.shop.domain.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResponse {

    private Long orderId;

    private String name;

    private Address address;

    private LocalDateTime orderDate;

    private OrderStatus status; //주문상태 [ORDER,CANCLE]

    public OrderResponse (Order order) {
        this.name = order.getUser().getName(); // LAZY 초기화
        this.orderId = order.getId();
        this.address = order.getUser().getAddress(); // LAZY 초기화
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus();
    }

}
