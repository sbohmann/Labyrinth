package mazeClient;

import org.apache.logging.slf4j.SLF4JLogger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class MazeClientTest {
    @Test
    void printProperties() {
        Logger.getLogger("io.netty").setLevel(Level.OFF);
        System.setProperty("debug", "false");
        System.setProperty("trace", "false");
        System.setProperty("logging.level.root", "OFF");
        System.setProperty("logging.level.org.springframework", "OFF");
        System.setProperty("logging.level.org.springframework.web", "OFF");
        System.setProperty("spring.main.banner-mode", "OFF");
    }

    @Test
    void getDirections() {
        MazeClient client = new MazeClient();
        client.getDirections();
        client.move(Direction.East);
        client.getDirections();
    }
}
