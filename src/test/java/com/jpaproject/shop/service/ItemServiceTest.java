package com.jpaproject.shop.service;

import com.jpaproject.shop.controller.item.BookForm;
import com.jpaproject.shop.domain.item.Book;
import com.jpaproject.shop.domain.item.Item;
import com.jpaproject.shop.domain.item.Movie;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ModelMapper modelMapper;

    @Test
    public void 상품저장() throws Exception {
        //given
        Movie newMovie = Movie.builder()
                .actor("고마츠 나나")
                .director("이와이 슌지")
                .price(2000)
                .name("오늘")
                .stockQuantity(10)
                .build();


        //when
        Long saveItem = itemService.saveItem(newMovie);

        //then
        assertEquals(newMovie,itemService.findOne(saveItem));

    }

    @Test
    public void 하위타입_체크() throws Exception {
        Movie newMovie = Movie.builder()
                .actor("고마츠 나나")
                .director("이와이 슌지")
                .price(2000)
                .name("오늘")
                .stockQuantity(10)
                .build();


        //when
        Long saveItem = itemService.saveItem(newMovie);
        Item item = itemService.findOne(saveItem);

        //then
        assertInstanceOf(Movie.class, item, "아이템은 영화입니다.");
    }

    @Test
    public void 하위타입_매핑() throws Exception {
        Book book = Book.builder()
                .name("name")
                .price(1000)
                .stockQuantity(3)
                .build();


        //when
        Long saveItem = itemService.saveItem(book);
        Book getBook = (Book) itemService.findOne(saveItem);
        BookForm form = modelMapper.map(getBook, BookForm.class);

        //then
        assertEquals(1000,form.getPrice());
    }

}