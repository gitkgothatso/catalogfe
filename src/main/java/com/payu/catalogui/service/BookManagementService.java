package com.payu.catalogui.service;

import com.payu.catalogui.client.BookClient;
import com.payu.catalogui.domain.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookManagementService {

    private final BookClient bookClient;

    public BookManagementService(BookClient bookClient) {
        this.bookClient = bookClient;
    }

    public List<Book> fetchAllBooks() {
            return bookClient.getAllBooks();
    }

    public Book fetchBookByISBN(long isbn){
        return bookClient.getBookByIsbn(isbn);
    }

    public boolean addBook(Book book) {
        try (Response response = bookClient.addBook(book)) {
            return response.getStatus() == Response.Status.CREATED.getStatusCode()
                    || response.getStatus() == Response.Status.OK.getStatusCode();
        }
    }

    public boolean updateBook(Long isbn, Book book) {
        try (Response response = bookClient.updateBook(isbn, book)) {
            return response.getStatus() == Response.Status.OK.getStatusCode();
        }
    }

    public boolean deleteBook(Long isbn) {
        try (Response response = bookClient.deleteBook(isbn)) {
            return response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()
                    || response.getStatus() == Response.Status.OK.getStatusCode();
        }catch (Exception e) {
            System.err.println("Failed to delete book: " + isbn);
            e.printStackTrace();
            return false;
        }
    }
}
