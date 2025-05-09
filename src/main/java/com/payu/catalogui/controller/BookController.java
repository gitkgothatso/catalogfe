package com.payu.catalogui.controller;


import com.payu.catalogui.domain.Book;
import com.payu.catalogui.domain.BookType;
import com.payu.catalogui.service.BookManagementService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@CrossOrigin("*")
public class BookController {


    private final BookManagementService bookManagementService;

    public BookController(BookManagementService bookManagementService) {
        this.bookManagementService = bookManagementService;
    }

    @GetMapping("/")
    public String index(Model model) {

        try {
            List<Book> books = bookManagementService.fetchAllBooks();

            model.addAttribute("books", books);
            model.addAttribute("bookTypes", BookType.values());
        } catch (Exception e) {
            model.addAttribute("error", "Books not found or service unavailable.");
        }

        return "index";
    }

    @PostMapping("/add")
    public String add(
                    @RequestParam long isbn,
                    @RequestParam String name,
                      @RequestParam BookType bookType,
                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate publishedDate,
                      @RequestParam BigDecimal price) {

        bookManagementService.addBook(new Book(isbn,name ,publishedDate, price, bookType));
        return "redirect:/";
    }

    @PostMapping("/update")
    public String update(
            @RequestParam long isbn,
                         @RequestParam String name,
                         @RequestParam BookType bookType,
                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate publishedDate,
                         @RequestParam BigDecimal price,
                         Model model) {

        Optional<Book> existingBook = Optional.ofNullable(bookManagementService.fetchBookByISBN(isbn));

        if(!existingBook.isPresent()){
            model.addAttribute("error", String.format("Book with isbn %d does not exists.", isbn));
        }else {
            existingBook.ifPresent(book -> {
                book.setName(name);
                book.setBookType(bookType);
                book.setPublishedDate(publishedDate);
                book.setPrice(price);
                book.setIsbn(isbn);
            });

            bookManagementService.updateBook(isbn,existingBook.get());
        }

        return "redirect:/";
    }

    @PostMapping("/delete/{isbn}")
    public String delete(@PathVariable long isbn) {
        Optional<Book> existingBook = Optional.ofNullable(bookManagementService.fetchBookByISBN(isbn));

        existingBook.ifPresent(book -> {
            bookManagementService.deleteBook(book.getIsbn());
        });
        return "redirect:/";
    }
}