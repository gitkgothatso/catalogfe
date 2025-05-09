package com.payu.catalogui.client;

import com.payu.catalogui.domain.Book;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import java.util.List;

public class BookClient {

    private final WebTarget baseTarget;

    public BookClient(WebTarget baseTarget) {
        this.baseTarget = baseTarget;
    }

    public List<Book> getAllBooks() {
        return baseTarget
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Book>>() {});
    }

    public Book getBookByIsbn(Long isbn) {
        try {
            return baseTarget
                    .path(String.valueOf(isbn))
                    .request(MediaType.APPLICATION_JSON)
                    .get(Book.class);
        } catch (Exception e) {
            System.err.println("Error fetching book with ISBN " + isbn + ": " + e.getMessage());
            return null;
        }
    }

    public Response addBook(Book book) {
        return baseTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(book, MediaType.APPLICATION_JSON));
    }

    public Response updateBook(Long isbn, Book book) {
        return baseTarget
                .path(String.valueOf(isbn))
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(book, MediaType.APPLICATION_JSON));
    }

    public Response deleteBook(Long isbn) {
        return baseTarget
                .path(String.valueOf(isbn))
                .request()
                .delete();
    }
}
