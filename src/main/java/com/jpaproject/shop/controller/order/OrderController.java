package com.jpaproject.shop.controller.order;

import com.jpaproject.shop.domain.Order;
import com.jpaproject.shop.repository.OrderRepository;
import com.jpaproject.shop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/orders")
    public List<Order> orders() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        return orders; //엔티티 그대로 노출 dto로 변환필요
    }
}
