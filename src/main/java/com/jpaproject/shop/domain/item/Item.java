package com.jpaproject.shop.domain.item;

import com.jpaproject.shop.controller.item.ItemDto;
import com.jpaproject.shop.domain.Category;
import com.jpaproject.shop.exception.NotEnoughStockException;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //== 비지니스로직 ==

    /**
     * stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;

        if(restStock < 0){
            throw new NotEnoughStockException("need more stock"); }
        this.stockQuantity = restStock;
    }

    /**
     * 아이템 수정
     */
    public void changeParentField(ItemDto item) {
        this.name = item.getName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
    }

    abstract public void changeChildField(ItemDto item);

}
