package com.jpaproject.shop.controller.order;

import com.jpaproject.shop.controller.ResultList;
import com.jpaproject.shop.domain.Order;
import com.jpaproject.shop.repository.OrderRepository;
import com.jpaproject.shop.repository.OrderSearch;
import com.jpaproject.shop.repository.query.OrderQueryDto;
import com.jpaproject.shop.repository.query.OrderQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;

    private final OrderQueryRepository orderQueryRepository;

    /*@GetMapping("/api/orders")
    public ResultList orders() {

        List<OrderResponse> collect = orderRepository.findAll(new OrderSearch())
                .stream().map(OrderResponse::new).collect(Collectors.toList());

        ResultList result = ResultList.builder()
                .data(collect)
                .build();

        return result;
    }*/ // 쿼리가 LAZY 때문에 너무 많이 나감 -> 리팩토링

    @GetMapping("/api/v1/orders")
    public ResultList ordersV1() {
        List<OrderResponse> orderResponseList =
                orderRepository.findAllWithUND().stream().map(OrderResponse::new).collect(Collectors.toList());

        ResultList<Object> result = ResultList.builder()
                .data(orderResponseList)
                .build();

        return result;
    }

    @GetMapping("/api/v2/orders")
    public ResultList ordersV2() {
        List<OrderQueryDto> orderResponseList =
                orderQueryRepository.findAllWithQ();

        ResultList<Object> result = ResultList.builder()
                .data(orderResponseList)
                .build();

        return result;
    }
}
