package com.jpaproject.shop.service;

import com.jpaproject.shop.domain.Delivery;
import com.jpaproject.shop.domain.Order;
import com.jpaproject.shop.domain.OrderItem;
import com.jpaproject.shop.domain.User;
import com.jpaproject.shop.domain.enums.DeliveryStatus;
import com.jpaproject.shop.domain.enums.OrderStatus;
import com.jpaproject.shop.domain.item.Item;
import com.jpaproject.shop.repository.ItemRepository;
import com.jpaproject.shop.repository.OrderRepository;
import com.jpaproject.shop.repository.OrderSearch;
import com.jpaproject.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long userId, Long itemId, int count) {
        //엔티티 조회
        User user = userRepository.findOne(userId);
        Item item = itemRepository.findOne(itemId);

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //배송정보 생성
        Delivery delivery = Delivery.builder()
                .address(user.getAddress())
                .status(DeliveryStatus.READY)
                .build();

        //주문 생성
        Order order = Order.createOrder(user, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    /**
     * 주문 검색
     */
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }
}
