package com.payu.catalogui.controller;


import com.payu.catalogui.domain.Book;
import com.payu.catalogui.domain.BookType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class BookController {

    private final List<Book> books = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("books", books);
        model.addAttribute("bookTypes", BookType.values());
        return "index";
    }

    @PostMapping("/add")
    public String add(@RequestParam String name,
                      @RequestParam BookType bookType,
                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate publishedDate,
                      @RequestParam BigDecimal price) {
        books.add(new Book(idGenerator.getAndIncrement(),name ,publishedDate, price, bookType));
        return "redirect:/";
    }

    @PostMapping("/update")
    public String update(
                         @RequestParam String name,
                         @RequestParam BookType bookType,
                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate publishedDate,
                         @RequestParam BigDecimal price,
                         @RequestParam int isbn) {
        books.stream()
                .filter(i -> i.getIsbn() == isbn)
                .forEach(i -> {
                    i.setName(name);
                    i.setBookType(bookType);
                    i.setPublishedDate(publishedDate);
                    i.setPrice(price);
                    i.setIsbn(isbn);
                });
        return "redirect:/";
    }

    @PostMapping("/delete/{isbn}")
    public String delete(@PathVariable int isbn) {
        books.removeIf(i -> i.getIsbn() == isbn);
        return "redirect:/";
    }
}