package mazeClient;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Objects;

public class AvailableDirections {
    private boolean north;
    private boolean east;
    private boolean south;
    private boolean west;

    @JsonGetter("North")
    public boolean getNorth() {
        return north;
    }

    @JsonSetter("North")
    public void setNorth(boolean north) {
        this.north = north;
    }

    @JsonGetter("East")
    public boolean getEast() {
        return east;
    }

    @JsonSetter("East")
    public void setEast(boolean east) {
        this.east = east;
    }

    @JsonGetter("South")
    public boolean getSouth() {
        return south;
    }

    @JsonSetter("South")
    public void setSouth(boolean south) {
        this.south = south;
    }

    @JsonGetter("West")
    public boolean getWest() {
        return west;
    }

    @JsonSetter("West")
    public void setWest(boolean west) {
        this.west = west;
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

    @Override
    public int hashCode() {
        return Objects.hash(north, east, south, west);
    }
}
