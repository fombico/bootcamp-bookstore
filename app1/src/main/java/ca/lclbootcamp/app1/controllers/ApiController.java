package ca.lclbootcamp.app1.controllers;

import ca.lclbootcamp.app1.models.Book;
import ca.lclbootcamp.app1.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ApiController {
    private BookRepository bookRepository;

    @PutMapping("/cart")
    public void addToCart(Book book) {
        bookRepository.save(book);
    }

    @GetMapping("/cart")
    public List<Book> getCart() {
        return bookRepository.findAll();
    }
}
