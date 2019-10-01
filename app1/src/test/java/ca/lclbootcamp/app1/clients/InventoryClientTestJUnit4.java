package ca.lclbootcamp.app1.clients;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

@RunWith(SpringRunner.class)
@RestClientTest(InventoryClient.class)
public class InventoryClientTestJUnit4 {

    @Autowired
    MockRestServiceServer mockRestServiceServer;

    @Autowired
    InventoryClient subject;

    @Test
    public void fetchInventorySize_callsApiAndReturnsInventorySize() {
        mockRestServiceServer.expect(requestTo("/inventory/size"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
                .andRespond(MockRestResponseCreators.withSuccess("{\"count\":20}", MediaType.APPLICATION_JSON));
        int inventorySize = subject.fetchInventorySize();
        assertThat(inventorySize).isEqualTo(20);
        mockRestServiceServer.verify();
    }

    @Test
    public void fetchInventorySize_returnsZero_whenCountIsNotProvided() {
        mockRestServiceServer.expect(requestTo("/inventory/size"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
                .andRespond(MockRestResponseCreators.withSuccess("{}", MediaType.APPLICATION_JSON));
        int inventorySize = subject.fetchInventorySize();
        assertThat(inventorySize).isEqualTo(0);
        mockRestServiceServer.verify();
    }
}