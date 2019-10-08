package ca.lclbootcamp.app1

import ca.lclbootcamp.app1.models.Book
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import spock.lang.Specification

class SpockTests extends Specification {

    def "calling put cart should return 200"() {
        given: "User adds a book"
        RestTemplate restTemplate = new RestTemplate()
        final Book book = Book.builder()
                .title("My First Novel")
                .releaseYear(2000L)
                .build()

        when: "User makes PUT call"
        def responseEntity = restTemplate.exchange("http://localhost:8080/cart", HttpMethod.PUT, new HttpEntity<Object>(book), Void.class)

        then: "API returns 200 OK"
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200)
    }
}
