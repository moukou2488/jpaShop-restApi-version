package com.jpaproject.shop.domain.item;

import com.jpaproject.shop.controller.item.ItemDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("M")
public class Movie extends Item {

    private String director;
    private String actor;

    @Override
    public void changeChildField(ItemDto item) {
        this.director = item.getDirector();
        this.actor = item.getActor();
    }
}
