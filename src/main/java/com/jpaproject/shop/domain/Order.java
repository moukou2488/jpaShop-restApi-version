package com.jpaproject.shop.domain;

import com.jpaproject.shop.domain.enums.DeliveryStatus;
import com.jpaproject.shop.domain.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id ")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER,CANCLE]

    @Builder
    private Order(OrderStatus orderStatus, LocalDateTime orderDate) {
        this.status = orderStatus;
        this.orderDate = orderDate;
    }

    //== 연관관계 메서드 ==
    public void userOrderAsct(User user) {
        this.user = user;
        user.getOrders().add(this);
    }

    public void deliveryOrderAsct(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }


    //== 생성메서드 ==
    public static Order createOrder(User user, Delivery delivery, OrderItem... orderItems) {
        Order order = Order.builder()
                .orderStatus(OrderStatus.ORDER)
                .orderDate(LocalDateTime.now())
                .build();

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.userOrderAsct(user);
        order.deliveryOrderAsct(delivery);

        return order;
    }

    //== 비지니스 로직 ==

    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.changeStatus(OrderStatus.CANCLE);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    /**
     * 주문 상태 변경
     */
    public void changeStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }

    //== 조회 로직 ==

    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
        return totalPrice;
    }

}
