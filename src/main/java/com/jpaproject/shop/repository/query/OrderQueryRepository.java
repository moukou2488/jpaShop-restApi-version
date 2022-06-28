package com.jpaproject.shop.repository.query;

import com.jpaproject.shop.controller.order.OrderResponse;
import com.jpaproject.shop.domain.Order;
import com.jpaproject.shop.domain.User;
import com.jpaproject.shop.repository.OrderSearch;
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
public class OrderQueryRepository {
    private final EntityManager entityManager;

    public List<OrderQueryDto> findAllWithQ() {
        return entityManager.createQuery(
                "select new com.jpaproject.shop.repository.query.OrderQueryDto(o.id, u.name,d.address,o.orderDate,o.status) from Order o " +
                        " join o.user u" +
                        " join o.delivery d", OrderQueryDto.class)
                .getResultList();
    }
}
