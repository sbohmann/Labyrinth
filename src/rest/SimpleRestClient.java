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

    public SimpleRestClient(String baseUrl) {
        client = WebClient
                .builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        objectMapper = new ObjectMapper();
    }


    public <T> T get(String path, Class<T> type) {
        return client
                .get()
                .uri(path)
                .retrieve()
                .bodyToMono(type)
                .block();
    }

    public <T> void post(String path, T value) {
        ClientResponse response = client
                .post()
                .uri(path)
                .syncBody(value)
                .exchange()
                .block();
        checkStatus(response);
    }

    public void post(String path) {
        ClientResponse response = client
                .post()
                .uri(path)
                .exchange()
                .block();
        checkStatus(response);
    }

    private void checkStatus(ClientResponse response) {
        HttpStatus status = response.statusCode();
        if (!status.is2xxSuccessful()) {
            throw new IllegalStateException("post received unexpected status code " + status);
        }
    }

    public <T> T convert(JsonNode content, Class<T> type) {
        try {
            return objectMapper.readerFor(type).readValue(content);
        } catch (IOException exception) {
            throw new IllegalArgumentException(exception);
        }
    }
}
