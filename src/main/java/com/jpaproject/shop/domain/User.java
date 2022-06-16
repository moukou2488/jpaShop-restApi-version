package com.jpaproject.shop.domain;

import com.jpaproject.shop.controller.user.UserRequest;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    //== 비지니스 로직 ==

    /**
     * 회원정보 수정
     */
    public void changeUserField(UserRequest userRequest) {
        this.name = userRequest.getName();
        this.address = userRequest.getAddress();
    }


}
