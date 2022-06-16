package com.jpaproject.shop.repository;

import com.jpaproject.shop.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager entityManager;

    public void save(User user) {
        entityManager.persist(user);
    }

    public User findOne(Long id) {
        return entityManager.find(User.class, id);
    }

    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    public List<User> findByName(String name) {
        return entityManager.createQuery("select u from User u where u.name = :name", User.class)
                .setParameter("name", name)
                .getResultList();
    }

}
