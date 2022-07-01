package com.jpaproject.shop.repository.query;

import lombok.Data;

@Data
public class ItemQueryResponse {
    private Long orderId;

    private String ItemName;

    private int orderPrice;

    private int count;

    public ItemQueryResponse(Long orderId, String itemName, int orderPrice, int count) {
        this.orderId = orderId;
        this.ItemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
