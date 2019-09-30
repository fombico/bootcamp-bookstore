package ca.lclbootcamp.app1.controllers;

import ca.lclbootcamp.app1.models.Book;
import ca.lclbootcamp.app1.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DisplayName("Api Controller Test - JUnit 5")
class ApiControllerTestJUnit5 {
    private final static Book book = Book.builder()
            .title("My First Novel")
            .releaseYear(2000L)
            .build();

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private ApiController subject;

    @Nested
    @DisplayName("Calling add to cart")
    class CallingAddToCart {
        @Test
        @DisplayName("Should save in database")
        void shouldSaveInDatabase() {
            subject.addToCart(book);
            verify(bookRepository).save(book);
        }
    }

    @Nested
    @DisplayName("Calling get cart")
    class CallingGetCart {
        private final List<Book> bookList = Collections.singletonList(book);
        private List<Book> returnedBooks;

        @BeforeEach
        void setup() {
            when(bookRepository.findAll())
                    .thenReturn(bookList);
            returnedBooks = subject.getCart();
        }

        @Test
        @DisplayName("Should call book repository - find all")
        void shouldCallBookRepositoryFindAll() {
            verify(bookRepository).findAll();
        }

        @Test
        @DisplayName("Should return all books")
        void shouldReturnAllBooks() {
            assertThat(returnedBooks).isEqualTo(bookList);
        }
    }
}
