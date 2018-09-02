package mazeClient;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AvailableDirections {
    private final boolean north;
    private final boolean east;
    private final boolean south;
    private final boolean west;

    public AvailableDirections(@JsonProperty("North") boolean north, @JsonProperty("East") boolean east, @JsonProperty("South") boolean south, @JsonProperty("West") boolean west) {
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
    }

    @JsonGetter("North")
    public boolean getNorth() {
        return north;
    }

    @JsonGetter("East")
    public boolean getEast() {
        return east;
    }

    @JsonGetter("South")
    public boolean getSouth() {
        return south;
    }

    @JsonGetter("West")
    public boolean getWest() {
        return west;
    }

    @Override
    public String toString() {
        return "AvailableDirections{" +
                "north=" + north +
                ", east=" + east +
                ", south=" + south +
                ", west=" + west +
                '}';
    }
}
