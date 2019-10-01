package ca.lclbootcamp.app1.clients;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(SpringExtension.class)
@DisplayName("Inventory Client Test - Junit 5")
@RestClientTest(InventoryClient.class)
class InventoryClientTestJUnit5 {

    @Autowired
    MockRestServiceServer mockRestServiceServer;

    @Autowired
    InventoryClient subject;

    @Nested
    @DisplayName("Fetch inventory size")
    class FetchInventorySize {

        @BeforeEach
        void setup() {
            mockRestServiceServer.reset();
        }

        @Test
        @DisplayName("returns inventory size")
        void returnsInventorySize() {
            mockRestServiceServer.expect(requestTo("/inventory/size"))
                    .andExpect(method(HttpMethod.GET))
                    .andExpect(header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
                    .andRespond(withSuccess("{\"count\":20}", MediaType.APPLICATION_JSON));
            int inventorySize = subject.fetchInventorySize();
            assertThat(inventorySize).isEqualTo(20);
            mockRestServiceServer.verify();
        }

        @Test
        @DisplayName("returns zero when count is not provided")
        void returnsZeroWhenCountIsNotProvided() {
            mockRestServiceServer.expect(requestTo("/inventory/size"))
                    .andExpect(method(HttpMethod.GET))
                    .andExpect(header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
                    .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));
            int inventorySize = subject.fetchInventorySize();
            assertThat(inventorySize).isEqualTo(0);
            mockRestServiceServer.verify();
        }
    }
}