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
        Object[] chunk = getChunk(x, y);
        int offset = ChunkAddress.chunkOffset(x, y);
        return (E) chunk[offset];
    }

    public void set(int x, int y, E value) {
        Object[] chunk = getChunk(x, y);
        int offset = ChunkAddress.chunkOffset(x, y);
        chunk[offset] = value;
    }

    private Object[] getChunk(int x, int y) {
        Long key = ChunkAddress.chunkKey(x, y);
        return chunks.computeIfAbsent(key, key_ -> new Object[ChunkAddress.ChunkSize]);
    }
}
