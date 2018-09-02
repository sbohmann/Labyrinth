package mazeClient;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class MazeClient {
    private final WebClient client;

    public MazeClient() {
        client = WebClient
                .builder()
                .baseUrl("http://localhost:3000")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public void getDirections() {
        AvailableDirections responseBody = client
                .get()
                .uri("/directions")
                .retrieve()
                .bodyToMono(AvailableDirections.class)
                .block();
        System.out.println("Response body: " + responseBody);
    }

    public void move(Direction direction) {
        client
                .post()
                .uri("/move")
                .syncBody(direction)
                .retrieve()
                .onStatus(status -> true, response -> {
                    System.out.println("POST response status: " + response.statusCode());
                    return Mono.empty();
                })
                .bodyToMono(String.class)
                .block();
    }

    private Mono<? extends Throwable> handleStatus(ClientResponse clientResponse) {
        System.out.println("Status: " + clientResponse.statusCode());
        return Mono.empty();
    }
}
