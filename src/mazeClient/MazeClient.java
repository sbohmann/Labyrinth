package mazeClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

public class MazeClient {
    private static Logger logger = LoggerFactory.getLogger(MazeClient.class);

    private final WebClient client;

    public MazeClient() {
        client = WebClient
                .builder()
                .baseUrl("http://localhost:3000")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public AvailableDirections getAvailableDirections() {
        AvailableDirections availableDirections = client
                .get()
                .uri("/directions")
                .retrieve()
                .bodyToMono(AvailableDirections.class)
                .block();
        logger.debug("getAvailableDirections esponse body: " + availableDirections);
        return availableDirections;
    }

    public State getState() {
        Map<String, String> stateAsMap = client
                .get()
                .uri("/state")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String,String>>() {})
                .block();
        logger.debug("getState esponse body: " + stateAsMap);
        return State.valueOf(stateAsMap.get("State"));
    }

    public void move(Direction direction) {
        ClientResponse response = client
                .post()
                .uri("/move")
                .syncBody(direction)
                .exchange()
                .block();
        HttpStatus status = response.statusCode();
        if (!status.is2xxSuccessful()) {
            throw new IllegalStateException("move received unexpected status code " + status);
        }
    }

    private Mono<? extends Throwable> handleStatus(ClientResponse clientResponse) {
        System.out.println("Status: " + clientResponse.statusCode());
        return Mono.empty();
    }
}
