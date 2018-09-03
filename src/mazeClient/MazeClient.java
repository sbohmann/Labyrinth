package mazeClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

public class MazeClient {
    private static Logger logger = LoggerFactory.getLogger(MazeClient.class);

    private final WebClient client;
    private final ObjectMapper objectMapper;

    public MazeClient() {
        client = WebClient
                .builder()
                .baseUrl("http://localhost:3000")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        objectMapper = new ObjectMapper();
    }

    public Position getPosition(){
        JsonNode content = get(
                "/position",
                JsonNode.class);
        return convert(content.get("Position"), Position.class);
    }

    public AvailableDirections getAvailableDirections() {
        return get(
                "/directions",
                AvailableDirections.class);
    }

    public State getState() {
        JsonNode content = get(
                "/state",
                JsonNode.class);
        return convert(content.get("State"), State.class);
    }

    public void move(Direction direction) {
        post("/move", direction);
    }

    private <T> void post(String path, T value) {
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

    private <T> T get(String path, Class<T> type) {
        return client
                .get()
                .uri(path)
                .retrieve()
                .bodyToMono(type)
                .block();
    }

    private <T> T convert(JsonNode content, Class<T> type) {
        try {
            return objectMapper.readerFor(type).readValue(content);
        } catch (IOException exception) {
            throw new IllegalArgumentException(exception);
        }
    }

    private Mono<? extends Throwable> handleStatus(ClientResponse clientResponse) {
        System.out.println("Status: " + clientResponse.statusCode());
        return Mono.empty();
    }
}
