package com.jpaproject.shop.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultList<T> {
    private T data;
}
