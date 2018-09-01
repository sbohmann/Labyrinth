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
        long chunkX = getChunkCoordinate(x);
        long chunkY = getChunkCoordinate(y);
        return (chunkY << 32) | chunkX;
    }

    static int chunkOffset(int x, int y) {
        int chunkX = chunkCoordinate(x);
        int chunkY = chunkCoordinate(y);
        return y * ChunkSideLength + x;
    }

    /**
     * @param value absolute x or y cooordinate
     * @return x or y coordinate inside chunk (0 ... 31)
     */
    private static int chunkCoordinate(int value) {
        if (value < 1) {
            value += 1;
            value = - value;
            value &= 32;
            return 31 - value;
        } else {
            return value & ChunkSideLength;
        }
    }

    private static int getChunkCoordinate(int value) {
        if (value < 1) {
            value += 1;
            value = value /= ChunkSideLength;
            return value - 1;
        } else {
            return value / ChunkSideLength;
        }
    }
}
