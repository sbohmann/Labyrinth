package rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class RestClient {
    private URI baseUrl;
    private final RestTemplate client;
    private final ObjectMapper objectMapper;

    public RestClient(String baseUrl) {
        this.baseUrl = uriFromString(baseUrl);
        client = new RestTemplate();
        objectMapper = new ObjectMapper();
    }


    public <T> T get(String path, Class<T> type) {
        return client.getForObject(uriForPath(path), type);
    }

    public <T> void post(String path, T value) {
        client.postForLocation(uriForPath(path), value);
    }

    public void post(String path) {
        client.postForLocation(uriForPath(path), null);
    }

    private URI uriFromString(String urlString) {
        try {
            return new URI(urlString);
        } catch (URISyntaxException exception) {
            throw new IllegalArgumentException(exception);
        }
    }

    private URI uriForPath(String path) {
        return baseUrl.resolve(path);
    }

    public <T> T convert(JsonNode content, Class<T> type) {
        try {
            return objectMapper.readerFor(type).readValue(content);
        } catch (IOException exception) {
            throw new IllegalArgumentException(exception);
        }
    }
}
