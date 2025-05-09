package com.payu.catalogui.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private Long isbn;
    private String name;
    private LocalDate publishedDate;
    private BigDecimal price;
    private BookType bookType;
}
