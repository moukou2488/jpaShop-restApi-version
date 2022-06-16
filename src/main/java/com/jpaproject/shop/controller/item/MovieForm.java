package com.jpaproject.shop.controller.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieForm {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private String director;
    private String actor;

}
