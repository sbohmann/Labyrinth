package mazeClient;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Position {
    private final int x;
    private final int y;

    public Position(@JsonProperty("X") int x, @JsonProperty("Y") int y) {
        this.x = x;
        this.y = y;
    }

    @JsonGetter("X")
    public int getX() {
        return x;
    }

    @JsonGetter("Y")
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
