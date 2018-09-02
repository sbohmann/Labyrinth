package mazeClient;

import org.junit.jupiter.api.Test;

class MazeClientTest {
    @Test
    void moveEast() {
        MazeClient client = new MazeClient();
        System.out.println(client.getState());
        System.out.println(client.getAvailableDirections());

        client.move(Direction.East);

        System.out.println(client.getState());
        System.out.println(client.getAvailableDirections());
    }
}
