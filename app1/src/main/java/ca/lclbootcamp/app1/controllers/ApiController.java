package ca.lclbootcamp.app1.controllers;

import ca.lclbootcamp.app1.models.Book;
import ca.lclbootcamp.app1.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ApiController {
    private BookRepository bookRepository;

    @PutMapping("/cart")
    public void addToCart(Book book) {
        bookRepository.save(book);
    }

    @GetMapping("/cart")
    public Iterable<Book> getCart() {
        return bookRepository.findAll();
    }

    @DeleteMapping("/cart")
    public void deleteByTitle(@RequestParam String title) {
        bookRepository.deleteBookByTitle(title);
    }
}
