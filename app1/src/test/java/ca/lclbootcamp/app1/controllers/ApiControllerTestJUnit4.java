package ca.lclbootcamp.app1.controllers;

import ca.lclbootcamp.app1.models.Book;
import ca.lclbootcamp.app1.repositories.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ApiControllerTestJUnit4 {
    private final static Book book = Book.builder()
            .title("My First Novel")
            .releaseYear(2000L)
            .build();

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private ApiController subject;


    @Test
    public void callingAddToCart_shouldSaveInDatabase() {
        subject.addToCart(book);
        verify(bookRepository).save(book);
    }

    @Test
    public void callingGetCart_shouldReturnAllBooks() {
        final List<Book> bookList = Collections.singletonList(book);
        when(bookRepository.findAll())
                .thenReturn(bookList);
        assertThat(subject.getCart()).isEqualTo(bookList);
        verify(bookRepository).findAll();
    }

    @Test
    public void callingDeleteByTitle_shouldDeleteBookByTitle() {
        subject.deleteByTitle("Harry Potter");
        verify(bookRepository).deleteBookByTitle("Harry Potter");
    }
}