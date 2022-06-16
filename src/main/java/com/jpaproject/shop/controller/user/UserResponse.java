package com.jpaproject.shop.controller.user;

import com.jpaproject.shop.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private Address address;

    @Data
    @AllArgsConstructor
    static class CreateUserResponse{
        private Long id;
    }

    @Data
    @AllArgsConstructor
    static class UpdateUserResponse{
        private Long id;
        private String name;
    }

}
