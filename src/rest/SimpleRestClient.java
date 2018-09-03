package rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

public class SimpleRestClient {
    private final WebClient client;
    private final ObjectMapper objectMapper;

    public SimpleRestClient() {
        client = WebClient
                .builder()
                .baseUrl("http://localhost:3000")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        objectMapper = new ObjectMapper();
    }


    public <T> void post(String path, T value) {
        ClientResponse response = client
                .post()
                .uri(path)
                .syncBody(value)
                .exchange()
                .block();
        HttpStatus status = response.statusCode();
        if (!status.is2xxSuccessful()) {
            throw new IllegalStateException("move received unexpected status code " + status);
        }
    }

    public <T> T get(String path, Class<T> type) {
        return client
                .get()
                .uri(path)
                .retrieve()
                .bodyToMono(type)
                .block();
    }

    public <T> T convert(JsonNode content, Class<T> type) {
        try {
            return objectMapper.readerFor(type).readValue(content);
        } catch (IOException exception) {
            throw new IllegalArgumentException(exception);
        }
    }
}
