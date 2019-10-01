package ca.lclbootcamp.app1.repositories;

import ca.lclbootcamp.app1.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    @Transactional
    void deleteBookByTitle(String title);

    List<Book> findAllByTitleContains(String title);
}
