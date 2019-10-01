package ca.lclbootcamp.app1.clients;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class InventoryClient {

    private final RestTemplate restTemplate;

    public InventoryClient(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .rootUri("http://localhost:8000")
                .build();
    }

    public int fetchInventorySize() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<ObjectNode> response = restTemplate.exchange("/inventory/size", HttpMethod.GET, httpEntity, ObjectNode.class);

        ObjectNode responseBody = response.getBody();
        if (responseBody != null && responseBody.has("count")) {
            return responseBody.get("count").asInt();
        } else {
            return 0;
        }
    }
}
