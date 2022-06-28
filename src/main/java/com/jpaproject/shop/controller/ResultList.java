package com.jpaproject.shop.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ResultList<T> {
    private T data;
}
