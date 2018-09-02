package solver;

import mazeClient.AvailableDirections;
import mazeClient.Direction;
import mazeClient.MazeClient;

class Solver {
    MazeClient client;

    Solver() {
        this.client = new MazeClient();
    }

    void run() {
        move(Direction.East);
        move(Direction.West);
    }

    private void move(Direction direction) {
        while (free(client.getAvailableDirections(), direction)) {
            client.move(direction);
        }
    }

    private boolean free(AvailableDirections availableDirections, Direction direction) {
        switch (direction) {
            case North:
                return availableDirections.getNorth();
            case East:
                return availableDirections.getEast();
            case South:
                return availableDirections.getSouth();
            case West:
                return availableDirections.getWest();
            default:
                throw new IllegalArgumentException("direction " + direction);
        }
    }
}
