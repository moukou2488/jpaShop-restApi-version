package com.jpaproject.shop.repository.query;

import com.jpaproject.shop.domain.Address;
import com.jpaproject.shop.domain.Order;
import com.jpaproject.shop.domain.OrderItem;
import com.jpaproject.shop.domain.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderQueryResponse {

    private Long orderId;

    private String name;

    private Address address;

    private LocalDateTime orderDate;

    private OrderStatus status; //주문상태 [ORDER,CANCLE]


    public OrderQueryResponse(Long orderId, String name, Address address, LocalDateTime orderDate, OrderStatus status) {
        this.name = name;
        this.orderId = orderId;
        this.address = address;
        this.orderDate = orderDate;
        this.status = status;
    }

    // == custom dto ==


}
