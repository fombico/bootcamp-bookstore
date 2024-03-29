package ca.lclbootcamp.app1.controllers;

import ca.lclbootcamp.app1.models.Book;
import ca.lclbootcamp.app1.repositories.BookRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DisplayName("Api Controller Integration Test - JUnit 5")
class ApiControllerIntegrationTestJUnit5 {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setup() {
        bookRepository.deleteAll();
    }

    @Nested
    @DisplayName("Calling put cart")
    class CallingPutCart {
        final Book book = Book.builder()
                .title("My First Novel")
                .releaseYear(2000L)
                .build();

        @Test
        @DisplayName("Should return 200 ok")
        void shouldReturn200Ok() throws Exception {
            mockMvc.perform(put("/cart")
                    .content(objectMapper.writeValueAsString(book))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("Calling get cart")
    class CallingGetCart {
        final Book book1 = Book.builder()
                .title("Harry Potter")
                .releaseYear(2010)
                .build();
        final Book book2 = Book.builder()
                .title("Lord of the Rings")
                .releaseYear(2011)
                .build();

        @Test
        @DisplayName("Should return all items in cart")
        void shouldReturnAllItemsInCart() throws Exception {
            List<Book> expectedBooks = Arrays.asList(book1, book2);
            bookRepository.save(book1);
            bookRepository.save(book2);

            MvcResult mvcResult = mockMvc.perform(get("/cart"))
                    .andExpect(status().isOk())
                    .andReturn();

            String expectedResponse = objectMapper.writeValueAsString(expectedBooks);
            String actualResponse = mvcResult.getResponse().getContentAsString();

            assertThat(actualResponse).isEqualTo(expectedResponse);
        }
    }

    @Nested
    @DisplayName("Calling delete by Title")
    class CallingDeleteByTitle {
        final Book book1 = Book.builder()
                .title("Harry Potter")
                .releaseYear(2010)
                .build();
        final Book book2 = Book.builder()
                .title("Lord of the Rings")
                .releaseYear(2011)
                .build();

        @Test
        @DisplayName("Should delete book by title")
        void shouldDeleteBookByTitle() throws Exception {
            bookRepository.save(book1);
            bookRepository.save(book2);

            mockMvc.perform(delete("/cart?title={title}", "Harry Potter"))
                    .andExpect(status().isOk())
                    .andReturn();

            MvcResult mvcResult = mockMvc.perform(get("/cart"))
                    .andExpect(status().isOk())
                    .andReturn();

            List<Book> books = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Book>>(){});
            assertThat(books.size()).isOne();
            assertThat(books.get(0)).isEqualTo(book2);
        }
    }

    @Nested
    @DisplayName("Calling get cart by filter")
    class CallingGetCartByFilter {
        final Book book1 = Book.builder()
                .title("Harry Potter")
                .releaseYear(2010)
                .build();
        final Book book2 = Book.builder()
                .title("Lord of the Rings")
                .releaseYear(2011)
                .build();
        final Book book3 = Book.builder()
                .title("Star Wars")
                .releaseYear(1977)
                .build();

        @Test
        @DisplayName("Should return Books released within the start and end year ordered by release year")
        void shouldReturnBooksReleasedWithinTheStartAndEndYearOrderedByReleaseYear() throws Exception {
            bookRepository.saveAll(Arrays.asList(book1, book2, book3));

            MvcResult mvcResult = mockMvc.perform(get("/cart/filter?startYear={startYear}&endYear={endYear}", "1975", "2010"))
                    .andExpect(status().isOk())
                    .andReturn();

            List<Book> books = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Book>>(){});
            assertThat(books.size()).isEqualTo(2);
            assertThat(books.get(0).getTitle()).isEqualTo("Star Wars");
            assertThat(books.get(1).getTitle()).isEqualTo("Harry Potter");
        }
    }
}
