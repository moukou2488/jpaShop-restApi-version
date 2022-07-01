package com.jpaproject.shop.repository.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    private final EntityManager entityManager;

    public List<OrderQueryResponse> findAll() {
        return entityManager.createQuery(
                "select new com.jpaproject.shop.repository.query.OrderQueryResponse(o.id, u.name,d.address,o.orderDate,o.status) from Order o " +
                        " join o.user u" +
                        " join o.delivery d", OrderQueryResponse.class)
                .getResultList();
    }


    public List<ItemOrderQueryResponse> findAllWithItem() {
        List<ItemOrderQueryResponse> result = findOrders();

        result.forEach(o ->
        {
            List<ItemQueryResponse> orderItems = findItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });

        return result;
    }

    public List<ItemOrderQueryResponse> findAllWithItemOptimization() {
        List<ItemOrderQueryResponse> result = findOrders();

        Map<Long, List<ItemQueryResponse>> orderItemMap = findOrderItemMap(toOrderIds(result));

        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;
    }

    private Map<Long, List<ItemQueryResponse>> findOrderItemMap(List<Long> orderIds) {
        List<ItemQueryResponse> orderItems = entityManager.createQuery(
                "select new com.jpaproject.shop.repository.query.ItemQueryResponse(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id in :orderIds", ItemQueryResponse.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        Map<Long, List<ItemQueryResponse>> orderItemMap =
                orderItems.stream()
                        .collect(Collectors.groupingBy(itemQueryResponse -> itemQueryResponse.getOrderId()));
        return orderItemMap;
    }

    private List<Long> toOrderIds(List<ItemOrderQueryResponse> result) {
        List<Long> orderIds = result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());
        return orderIds;
    }


    private List<ItemQueryResponse> findItems(Long orderId) {
        return entityManager.createQuery(
                "select new com.jpaproject.shop.repository.query.ItemQueryResponse(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id = :orderId", ItemQueryResponse.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    private List<ItemOrderQueryResponse> findOrders() {
        return entityManager.createQuery(
                "select new com.jpaproject.shop.repository.query.ItemOrderQueryResponse(o.id, u.name, d.address, o.orderDate, o.status)" +
                        " from Order o" +
                        " join o.user u" +
                        " join o.delivery d", ItemOrderQueryResponse.class)
                .getResultList();
    }
}
