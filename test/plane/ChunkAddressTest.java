package plane;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static plane.ChunkAddress.*;

class ChunkAddressTest {
    @Test
    void simpleChunkKeys() {
        assertEquals(0L, chunkKey(0, 0));
        assertEquals(0L, chunkKey(1, 0));
        assertEquals(0L, chunkKey(0, 1));
        assertEquals(0L, chunkKey(1, 1));
        assertEquals(0L, chunkKey(31, 31));
        assertEquals(1L, chunkKey(32, 31));
        assertEquals(1L << 32, chunkKey(31, 32));
    }

    @Test
    void sipleChunkOffsets() {
        assertEquals(0, chunkOffset(0, 0));
        assertEquals(1, chunkOffset(1, 0));
        assertEquals(32, chunkOffset(0, 1));
        assertEquals(1023, chunkOffset(31, 31));
    }

    @Test
    void simpleChunkCoordinatesInGrid() {
        assertEquals(-3, getChunkCoordinateInGrid(-65));
        assertEquals(-2, getChunkCoordinateInGrid(-64));
        assertEquals(-2, getChunkCoordinateInGrid(-33));
        assertEquals(-1, getChunkCoordinateInGrid(-32));
        assertEquals(-1, getChunkCoordinateInGrid(-1));
        assertEquals(0, getChunkCoordinateInGrid(0));
        assertEquals(0, getChunkCoordinateInGrid(1));
        assertEquals(0, getChunkCoordinateInGrid(31));
        assertEquals(1, getChunkCoordinateInGrid(32));
        assertEquals(1, getChunkCoordinateInGrid(63));
        assertEquals(2, getChunkCoordinateInGrid(64));
    }

    @Test
    void simpleCoordinatesInsideChunk() {
    }
}