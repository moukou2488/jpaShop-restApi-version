package com.jpaproject.shop.service;

import com.jpaproject.shop.domain.Address;
import com.jpaproject.shop.domain.Order;
import com.jpaproject.shop.domain.User;
import com.jpaproject.shop.domain.enums.OrderStatus;
import com.jpaproject.shop.domain.item.Book;
import com.jpaproject.shop.exception.NotEnoughStockException;
import com.jpaproject.shop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager entityManager;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        User user = createUser();

        Book book = createBook("누구나 언제라도", 10000, 10);

        int orderCount = 4;

        //when
        Long orderId = orderService.order(user.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.ORDER, getOrder.getStatus(),"상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류수가 같아야한다");
        assertEquals(10000 * orderCount, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다");
        assertEquals(6, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야한다.");


    }

    @Test
    public void 주문취소() throws Exception {
        //given
        User user = createUser();
        Book book = createBook("굴러가는 나무", 5000, 5);

        int orderCount = 3;

        Long orderId = orderService.order(user.getId(), book.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCLE, getOrder.getStatus(), "주문 취소시 상태는 CANCEL이다.");
        assertEquals(5, book.getStockQuantity(), "주문 취소된 상품은 재고 수량이 다시 채워져야한다.");
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //given
        User user = createUser();

        Book book = createBook("이름 모를 새", 28000, 2);

        int orderCount = 4;

        //when then
        assertThrows(NotEnoughStockException.class, () -> orderService.order(user.getId(), book.getId(), orderCount));

    }

    private User createUser() {
        User user = User.builder()
                .name("유림")
                .address(new Address("서울","혜화","568-77"))
                .build();
        entityManager.persist(user);
        return user;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = Book.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .build();
        entityManager.persist(book);
        return book;
    }
}