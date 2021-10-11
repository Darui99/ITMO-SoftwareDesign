public class LRUCache<K, V> {

    private final int capacity;
    private final DoublyLinkedList<K, V> list;

    public LRUCache(int capacity) {
        list = new DoublyLinkedList<>();
        this.capacity = capacity;
    }

    public V get(final K key) {
        final int size = size();
        assert (size <= capacity);
        V res;
        if (containsKey(key)) {
            list.moveToBot(key);
            res = list.get(key);
        } else {
            res = null;
        }
        assert (size() == size);
        return res;
    }

    public void put(final K key, final V val) {
        final int size = size();
        assert (size <= capacity);
        if (containsKey(key)) {
            list.moveToBot(key);
            list.set(key, val);
            assert(size() == size);
        } else {
            if (size() >= capacity) {
                list.removeTop();
            }
            list.addToBot(key, val);
            assert (size() <= capacity);
            assert(size == capacity && size() == size || size < capacity && size() == size + 1);
        }
    }

    public boolean containsKey(final K key) {
        return list.containsKey(key);
    }

    public int size() {
        return list.size();
    }
}
