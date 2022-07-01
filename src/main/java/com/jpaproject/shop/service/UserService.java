package com.jpaproject.shop.service;

import com.jpaproject.shop.controller.user.UserRequest;
import com.jpaproject.shop.domain.User;
import com.jpaproject.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    //회원가입
    @Transactional
    public Long join(User user) {
        validateDuplicateUser(user); //중복 회원 검증
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateUser(User user) {
        List<User> findUsers = userRepository.findByName(user.getName());
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }


    //회원 전체 조회
    public List<User> findUsers(int offset) {
        return userRepository.findAll(offset);
    }

    //단일 회원 조회
    public User findOne(Long userId) {
        return userRepository.findOne(userId);
    }

    //회원 정보 수정
    @Transactional
    public void update(Long id, UserRequest userRequest) {
        User user = userRepository.findOne(id);
        user.changeUserField(userRequest);
    }
}
