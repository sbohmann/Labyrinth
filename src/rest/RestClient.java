package rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class RestClient {
    private URI baseUrl;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public RestClient(String baseUrl) {
        this.baseUrl = uriFromString(baseUrl);
        restTemplate = new RestTemplate();
        objectMapper = new ObjectMapper();
    }


    public <T> T get(String path, Class<T> type) {
        return restTemplate.getForObject(uriForPath(path), type);
    }

    public <T> T getElement(String path, Class<T> type, String ... keys) {
        JsonNode value = get(
                path,
                JsonNode.class);
        value = getSubNode(value, keys);
        return convert(value, type);
    }

    private JsonNode getSubNode(JsonNode value, String[] keys) {
        for (String key : keys) {
            value = value.get(key);
        }
        return value;
    }

    public <T> void post(String path, T value) {
        restTemplate.postForLocation(uriForPath(path), value);
    }

    public void post(String path) {
        restTemplate.postForLocation(uriForPath(path), null);
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

    private <T> T convert(JsonNode content, Class<T> type) {
        try {
            return objectMapper.readerFor(type).readValue(content);
        } catch (IOException exception) {
            throw new IllegalArgumentException(exception);
        }
    }
}
