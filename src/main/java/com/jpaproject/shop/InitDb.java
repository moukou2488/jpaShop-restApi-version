package com.jpaproject.shop;

import com.jpaproject.shop.domain.*;
import com.jpaproject.shop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager entityManager;

        public void dbInit1() {
            User user = createUser("황유림", "서울", "혜화", "548-87");
            entityManager.persist(user);

            Book book1 = createBook("처음 배우는 말", "오은영", "45ㄱ87ㄷ7", 50, 18000);

            entityManager.persist(book1);

            Book book2 = createBook("끊임없이 나오는 고민들", "김고민", "78t874e5", 40, 19000);

            entityManager.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, book1.getPrice(), 2);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, book2.getPrice(), 3);

            Delivery deliveryA = Delivery.createDelivery(user.getAddress());

            Order order = Order.createOrder(user, deliveryA, orderItem1, orderItem2);

            entityManager.persist(order);


        }

        public void dbInit2() {
            User user = createUser("이보영", "부산", "남포동", "507-4");
            entityManager.persist(user);

            Book book1 = createBook("같이 배우는 말하기", "오은영", "78e4w54", 100, 15000);

            entityManager.persist(book1);

            Book book2 = createBook("타나카씨는 궁금해", "나카무라 신이치", "853f5eff", 20, 8000);

            entityManager.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, book1.getPrice(), 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, book2.getPrice(), 4);

            Delivery deliveryA = Delivery.createDelivery(user.getAddress());

            Order order = Order.createOrder(user, deliveryA, orderItem1, orderItem2);

            entityManager.persist(order);


        }

        private User createUser(String userName, String city, String street, String zipcode) {
            return User.builder()
                    .name(userName)
                    .address(new Address(city, street, zipcode))
                    .build();
        }

        private Book createBook(String name, String author, String isbn, int stockQuantity, int price) {
            return Book.builder()
                    .name(name)
                    .author(author)
                    .isbn(isbn)
                    .stockQuantity(stockQuantity)
                    .price(price)
                    .build();
        }
    }
}
