package mazeClient;

import com.fasterxml.jackson.databind.JsonNode;
import rest.SimpleRestClient;

public class MazeClient {
    SimpleRestClient client;

    public MazeClient() {
        client = new SimpleRestClient("http://localhost:3000");
    }

    public Position getPosition(){
        JsonNode content = client.get(
                "/position",
                JsonNode.class);
        return client.convert(content.get("Position"), Position.class);
    }

    public AvailableDirections getAvailableDirections() {
        return client.get(
                "/directions",
                AvailableDirections.class);
    }

    public State getState() {
        JsonNode content = client.get(
                "/state",
                JsonNode.class);
        return client.convert(content.get("State"), State.class);
    }

    public void move(Direction direction) {
        client.post("/move", direction);
    }
}
