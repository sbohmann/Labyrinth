package mazeClient;

import rest.RestClient;

public class MazeClient {
    private final RestClient client;

    public MazeClient() {
        client = new RestClient("http://localhost:3000");
    }

    public Position getPosition(){
        return client.getElement(
                "/position",
                Position.class,
                "Position");
    }

    public AvailableDirections getAvailableDirections() {
        return client.get(
                "/directions",
                AvailableDirections.class);
    }

    public State getState() {
        return client.getElement(
                "/state",
                State.class,
                "State");
    }

    public void move(Direction direction) {
        client.post("/move", direction);
    }

    public void reset() {
        client.post("/reset");
    }
}
