package plane;

import java.util.HashMap;
import java.util.Map;

public class Plane<E> {
    private Map<Long,Object[]> chunks;

    public Plane() {
        this.chunks = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public E get(int x, int y) {
        Object[] chunk = getChunkOrNull(x, y);
        if (chunk == null) {
            return null;
        }
        int offset = ChunkAddress.chunkOffset(x, y);
        return (E) chunk[offset];
    }

    public void set(int x, int y, E value) {
        Object[] chunk = getOrCreateChunk(x, y);
        int offset = ChunkAddress.chunkOffset(x, y);
        chunk[offset] = value;
    }

    private Object[] getChunkOrNull(int x, int y) {
        Long key = ChunkAddress.chunkKey(x, y);
        return chunks.get(key);
    }

    private Object[] getOrCreateChunk(int x, int y) {
        Long key = ChunkAddress.chunkKey(x, y);
        return chunks.computeIfAbsent(key, key_ -> new Object[ChunkAddress.ChunkSize]);
    }
}
