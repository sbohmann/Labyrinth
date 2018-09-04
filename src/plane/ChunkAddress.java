package plane;

class ChunkAddress {
    private static final int ChunkSideLength = 32;
    static final int ChunkSize = ChunkSideLength * ChunkSideLength;

    /**
     * @param x absolute x coordinate
     * @param y absolute y coordinate
     * @return a unique key for each x, y coorinate pair
     */
    static long chunkKey(int x, int y) {
        long chunkX = getChunkCoordinateInGrid(x);
        long chunkY = getChunkCoordinateInGrid(y);
        return (chunkY << 32) | (chunkX & 0xffff_ffffL);
    }

    static int chunkOffset(int x, int y) {
        int chunkX = getCoordinateInsideChunk(x);
        int chunkY = getCoordinateInsideChunk(y);
        return chunkY * ChunkSideLength + chunkX;
    }

    static int getChunkCoordinateInGrid(int value) {
        if (value < 0) {
            value += 1;
            value = value /= ChunkSideLength;
            return value - 1;
        } else {
            return value / ChunkSideLength;
        }
    }

    /**
     * @param value absolute x or y cooordinate
     * @return x or y coordinate inside chunk (0 ... 31)
     */
    private static int getCoordinateInsideChunk(int value) {
        if (value < 0) {
            value += 1;
            value = - value;
            value %= ChunkSideLength;
            return 31 - value;
        } else {
            return value % ChunkSideLength;
        }
    }
}
