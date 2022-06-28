package com.jpaproject.shop.repository.query;

import com.jpaproject.shop.domain.Address;
import com.jpaproject.shop.domain.Order;
import com.jpaproject.shop.domain.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderQueryDto {

    private Long orderId;

    private String name;

    private Address address;

    private LocalDateTime orderDate;

    private OrderStatus status; //주문상태 [ORDER,CANCLE]


    public OrderQueryDto(Long orderId, String name, Address address, LocalDateTime orderDate, OrderStatus status) {
        this.name = name;
        this.orderId = orderId;
        this.address = address;
        this.orderDate = orderDate;
        this.status = status;
    }

}
