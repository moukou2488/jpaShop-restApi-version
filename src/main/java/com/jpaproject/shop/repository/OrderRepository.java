package com.jpaproject.shop.repository;

import com.jpaproject.shop.controller.order.OrderResponse;
import com.jpaproject.shop.domain.Order;
import com.jpaproject.shop.domain.QOrder;
import com.jpaproject.shop.domain.QUser;
import com.jpaproject.shop.domain.User;
import com.jpaproject.shop.domain.enums.OrderStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager entityManager;

    public void save(Order order) {
        entityManager.persist(order);
    }

    public Order findOne(Long id) {
        return entityManager.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        JPAQueryFactory query = new JPAQueryFactory(entityManager);

        QOrder order = QOrder.order;
        QUser user = QUser.user;

        return query
                .select(order)
                .from(order)
                .join(order.user, user)
                .limit(1000)
                .where(statusEq(orderSearch.getOrderStatus()), nameLike(orderSearch.getUserName()))
                .fetch();

    }

    private BooleanExpression nameLike(String username) {
        if(!StringUtils.hasText(username)) return null;
        return QUser.user.name.like(username);
    }

    private BooleanExpression statusEq(OrderStatus statusCond) {
        if(statusCond == null) return null;

        return QOrder.order.status.eq(statusCond);
    }



    public List<Order> findAllWithUND(int offset) {
        return entityManager.createQuery(
                "select o from Order o " +
                " join fetch o.user u" +
                " join fetch o.delivery d", Order.class)
                .setFirstResult(offset)
                .setMaxResults(100)
                .getResultList();
    }

    public List<Order> findAllWithItem() {
        return entityManager.createQuery(
                "select distinct o from Order o " +
                        " join fetch o.user u" +
                        " join fetch o.delivery d" +
                        " join fetch o.orderItems oi" +
                        " join fetch oi.item i", Order.class)
                //.setFirstResult(0) toMany에서 distinct를 사용하면 페이징 불가능
                //.setMaxResults(10)
                .getResultList();
    }

}
