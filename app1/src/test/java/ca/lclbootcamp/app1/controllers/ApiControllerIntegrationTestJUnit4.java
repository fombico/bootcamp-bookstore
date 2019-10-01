package ca.lclbootcamp.app1.controllers;

import ca.lclbootcamp.app1.models.Book;
import ca.lclbootcamp.app1.repositories.BookRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ApiControllerIntegrationTestJUnit4 {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @Before
    public void setup() {
        bookRepository.deleteAll();
    }

    @Test
    public void callingPutCart_shouldReturn200Ok() throws Exception {
        final Book book = Book.builder()
                .title("My First Novel")
                .releaseYear(2000L)
                .build();

        mockMvc.perform(put("/cart")
                .content(objectMapper.writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void callingGetCart_shouldReturnAllItemsInCart() throws Exception {
        final Book book1 = Book.builder()
                .title("Harry Potter")
                .releaseYear(2010)
                .build();
        final Book book2 = Book.builder()
                .title("Lord of the Rings")
                .releaseYear(2011)
                .build();

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

    @Test
    public void callingDeleteByTitle_shouldDeleteBookByTitle() throws Exception {
        final Book book1 = Book.builder()
                .title("Harry Potter")
                .releaseYear(2010)
                .build();
        final Book book2 = Book.builder()
                .title("Lord of the Rings")
                .releaseYear(2011)
                .build();
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

    @Test
    public void callingGetCartByFilter_shouldReturnBooksReleasedWithinTheStartAndEndYearOrderedByReleaseYear() throws Exception {
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
