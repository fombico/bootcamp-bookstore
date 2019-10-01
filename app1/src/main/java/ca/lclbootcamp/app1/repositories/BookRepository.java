package ca.lclbootcamp.app1.repositories;

import ca.lclbootcamp.app1.models.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookRepository {

    private Map<String, Book> books = new HashMap<>();

    public void save(Book book) {
        books.put(book.getTitle(), book);
    }

    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    public void deleteAll() {
        books.clear();
    }

    public void deleteBookByTitle(String title) {
        books.remove(title);
    }
}
