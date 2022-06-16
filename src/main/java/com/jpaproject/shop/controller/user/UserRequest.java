package com.jpaproject.shop.controller.user;

import com.jpaproject.shop.domain.Address;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Data
public class UserRequest {

    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;

    private Address address;
}
