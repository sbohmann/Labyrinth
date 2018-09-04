package plane;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlaneTest {
    private static final int NumberOfPositions = 10_000_000;

    private static Random random = new Random();

    private Map<Position, Integer> data = new HashMap<>();
    private Plane<Integer> plane = new Plane<>();

    @BeforeAll
    private void fillPlandAndData() {
        loop(() -> {
            Position position = randomPosition();
            int value = random.nextInt();

            Assertions.assertEquals(data.get(position), plane.get(position.x, position.y));
            data.putIfAbsent(position, value);
            if (plane.get(position.x, position.y) == null) {
                plane.set(position.x, position.y, value);
            }
            Assertions.assertEquals(data.get(position), plane.get(position.x, position.y));
        });
        System.out.println("data.size(): " + data.size());
        System.out.println("number of chunks: "+ plane.numberOfChunks());
    }

    @Test
    void positionsInData() {
        data.forEach((position, value) ->
                Assertions.assertEquals(value, plane.get(position.x, position.y)));
    }

    @Test
    void randomPositions() {
        int positionsFound = count(() -> {
            Position position = randomPosition();
            Integer positionFromData = data.get(position);
            Assertions.assertEquals(positionFromData, plane.get(position.x, position.y));
            return positionFromData != null;
        });
        System.out.println("Found " + positionsFound + " out of " + NumberOfPositions + " positions");
    }

    private Position randomPosition() {
        return new Position(
                randomCoordinate(),
                randomCoordinate());
    }

    private int randomCoordinate() {
        return (int) (random.nextGaussian() * 128);
    }

    private void loop(Runnable action) {
        int loopCount = 0;
        for (int index = 0; index < NumberOfPositions; ++index) {
            action.run();
            reportLoopCount(++loopCount);
        }
    }

    private int count(Supplier<Boolean> action) {
        int loopCount = 0;
        int count = 0;
        for (int index = 0; index < NumberOfPositions; ++index) {
            if (action.get()) {
                ++count;
            }
            reportLoopCount(++loopCount);
        }
        return count;
    }

    private void reportLoopCount(int value) {
        if (value % 1_000_000 == 0) {
            System.out.printf(
                    "%6.3f%%%n",
                    100.0 * value / NumberOfPositions);
        }
    }
}
