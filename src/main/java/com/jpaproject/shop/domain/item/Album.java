package com.jpaproject.shop.domain.item;

import com.jpaproject.shop.controller.item.ItemRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("A")
public class Album extends Item {

    private String artist;
    private String etc;

    @Override
    public void changeChildField(ItemRequest item) {
        this.artist = item.getArtist();
        this.etc = item.getEtc();
    }
}
