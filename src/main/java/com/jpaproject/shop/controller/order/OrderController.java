package com.jpaproject.shop.controller.order;

import com.jpaproject.shop.controller.ResultList;
import com.jpaproject.shop.repository.OrderRepository;
import com.jpaproject.shop.repository.OrderSearch;
import com.jpaproject.shop.repository.query.ItemOrderQueryResponse;
import com.jpaproject.shop.repository.query.OrderQueryResponse;
import com.jpaproject.shop.repository.query.OrderQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;

    private final OrderQueryRepository orderQueryRepository;

    //아이템 단품 주문 조회 ver.쿼리
    public ResultList ordersQuery() {
        List<OrderQueryResponse> orderResponseList =
                orderQueryRepository.findAll();

        return ResultList.builder()
                .data(orderResponseList)
                .build();
    }

    //아이템 단품 주문 조회 ver.패치조인

    public ResultList ordersFetch(@RequestParam(value = "offset", defaultValue = "0") int offset) {
        List<OrderResponse> orderResponseList =
                orderRepository.findAllWithUND(offset).stream().map(OrderResponse::new).collect(Collectors.toList());

        return ResultList.builder()
                .data(orderResponseList)
                .build();
    }


    //아이템 여러건 주문 조회 사용 X ver.LAZY 초기화
    @GetMapping("/api/orders")
    public ResultList ItemListOrdersLazy(@RequestBody OrderSearch orderSearch) {
        List<OrderResponse.ItemsOrderResponse> collect = orderRepository.findAll(orderSearch)
                .stream()
                .map(OrderResponse.ItemsOrderResponse::new) //생성자에서 LAZY 초기화를 통해 값 세팅 -> 쿼리가 너무 많이 나가게 됨
                .collect(Collectors.toList());

        return ResultList.builder()
                .data(collect)
                .build();
    }

    //아이템 여러건 주문 조회 but 중복데이터가 어플리케이션에 넘어옴 & 페이징 안됨 ver.distinct fetch join
    public ResultList ItemListOrdersDistnct() {
        List<OrderResponse.ItemsOrderResponse> collect = orderRepository.findAllWithItem()
                .stream()
                .map(OrderResponse.ItemsOrderResponse::new)
                .collect(Collectors.toList());

        return ResultList.builder()
                .data(collect)
                .build();

    }

    //아이템 여러건 주문 조회 사용 X ver.쿼리 n+1 문제 발생생
    public ResultList ItemListOrdersQuery() {
        List<ItemOrderQueryResponse> resultList = orderQueryRepository.findAllWithItem();

        return ResultList.builder()
                .data(resultList)
                .build();

    }

    //아이템 여러건 주문 조회 ver.쿼리 in절을 사용해 n+1 해결
    public ResultList ItemListOrdersQueryMap() {
        List<ItemOrderQueryResponse> orders = orderQueryRepository.findAllWithItemOptimization();

        return ResultList.builder()
                .data(orders)
                .build();
    }

    //아이템 여러건 주문 조회 ver.default_batch_fetch_size를 이용한 LAZY 초기화 문제 해결
    @GetMapping("/api/orders/items")
    public ResultList ItemListOrders(@RequestParam(value = "offset", defaultValue = "0") int offset) {
        List<OrderResponse.ItemsOrderResponse> orders =
                orderRepository.findAllWithUND(offset)
                        .stream()
                        .map(OrderResponse.ItemsOrderResponse::new)
                        .collect(Collectors.toList());

        return ResultList.builder()
                .data(orders)
                .build();
    }

}
