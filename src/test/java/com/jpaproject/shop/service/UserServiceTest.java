package com.jpaproject.shop.service;

import com.jpaproject.shop.domain.Address;
import com.jpaproject.shop.domain.User;
import com.jpaproject.shop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    @Rollback(value = false)
    public void 회원가입() throws Exception {
        //given
        User newUser = User.builder().name("mouou")
                .address(new Address("서울", "이촌", "568-452")).build();

        //when
        Long savedId = userService.join(newUser);

        //then
        assertEquals(newUser, userRepository.findOne(savedId));

    }

    @Test
    public void 중복회원_예외() throws Exception {
        //given
        User user1 = User.builder()
                .name("mouou")
                .address(new Address("서울", "이촌", "568-452"))
                .build();

        User user2 = User.builder()
                .name("mouou")
                .address(new Address("서울", "혜화", "887-4"))
                .build();

        //when
        userService.join(user1);

        //then
        assertThrows(IllegalStateException.class, () -> userService.join(user2));
    }

}