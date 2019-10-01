package ca.lclbootcamp.app1.repositories;

import ca.lclbootcamp.app1.models.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTestJUnit4 {

    @Autowired
    BookRepository subject;

    @Sql("/insertBook.sql")
    @Test
    public void findAllByTitleContains_returnsBooksThatContainTitle() {
        List<Book> books = subject.findAllByTitleContains("Harry Potter");
        List<String> titles = books.stream().map(Book::getTitle).collect(Collectors.toList());
        assertThat(titles).hasSize(2);
        assertThat(titles).contains("Harry Potter - A New Hope");
        assertThat(titles).contains("Harry Potter - Voldemort Strikes Back");
    }
}