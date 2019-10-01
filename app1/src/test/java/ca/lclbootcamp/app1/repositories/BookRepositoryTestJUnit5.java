package ca.lclbootcamp.app1.repositories;

import ca.lclbootcamp.app1.models.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DisplayName("BookRepositoryTest - JUnit 5")
@DataJpaTest
class BookRepositoryTestJUnit5 {

    @Autowired
    BookRepository subject;

    @Nested
    @DisplayName("Find all by title contains")
    @DataJpaTest
    class FindAllByTitleContains {

        @Test
        @DisplayName("Returns books that contain title")
        @Sql("/insertBook.sql")
        void returnsBooksThatContainTitle() {
            List<Book> books = subject.findAllByTitleContains("Harry Potter");
            List<String> titles = books.stream().map(Book::getTitle).collect(Collectors.toList());
            assertThat(titles).hasSize(2);
            assertThat(titles).contains("Harry Potter - A New Hope");
            assertThat(titles).contains("Harry Potter - Voldemort Strikes Back");
        }
    }

    @Nested
    @DisplayName("Find all by releaseYear is between, order by releaseYear asc")
    @DataJpaTest
    class FindAllByReleaseYearIsBetweenOrderByReleaseYearAsc {

        @Test
        @DisplayName("returns books within years, ordered by releaseYear asc")
        @Sql("/insertBook.sql")
        void returnsBooksWithinYearsOrderedByReleaseYearAsc() {
            List<Book> books = subject.findAllByReleaseYearIsBetweenOrderByReleaseYearAsc(1980L, 2010L);
            assertThat(books).hasSize(3);
            assertThat(books.get(0).getTitle()).isEqualTo("Star Wars - Prisoner of the Death Star");
            assertThat(books.get(1).getTitle()).isEqualTo("Star Wars - Goblet of Juice");
            assertThat(books.get(2).getTitle()).isEqualTo("Harry Potter - Voldemort Strikes Back");
        }
    }
}
