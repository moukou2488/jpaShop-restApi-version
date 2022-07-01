package com.jpaproject.shop.domain.item;

import com.jpaproject.shop.controller.item.ItemRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Setter
@Entity
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("B")
public class Book extends Item {

    private String author;
    private String isbn;


    @Override
    public void changeChildField(ItemRequest item) {
        this.author = item.getAuthor();
        this.isbn = item.getIsbn();
    }
}
