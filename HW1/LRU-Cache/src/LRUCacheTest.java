import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LRUCacheTest {
    @Test
    public void test1_putAndGet() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(3);
        cache.put(0, 0);
        assertEquals(cache.get(0), 0);
    }

    @Test
    public void test2_putAndUpdate() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(3);
        cache.put(0, 0);
        assertEquals(cache.get(0), 0);
        cache.put(0, 1);
        assertEquals(cache.get(0), 1);
    }

    @Test
    public void test3_replace() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(1);
        cache.put(0, 0);
        assertEquals(cache.get(0), 0);
        cache.put(1, 1);
        assertEquals(cache.get(1), 1);
        assertTrue(cache.containsKey(1));
        assertFalse(cache.containsKey(0));
        assertNull(cache.get(0));
    }

    @Test
    public void test4_size() {
        LRUCache<String, Integer> cache = new LRUCache<>(3);
        assertEquals(cache.size(), 0);
        cache.put("a", 0);
        assertEquals(cache.size(), 1);
        cache.put("a", -1);
        assertEquals(cache.size(), 1);
        cache.put("b", 1);
        assertEquals(cache.size(), 2);
        cache.put("c", 0);
        assertEquals(cache.size(), 3);
        cache.put("d", 0);
        assertEquals(cache.size(), 3);
    }

    @Test
    public void test5() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(3);
        cache.put(0, 0);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.get(0);
        cache.put(3, 3);
        assertFalse(cache.containsKey(1));
        cache.put(3, 4);
        assertEquals(cache.get(3), 4);
        cache.put(0, 5);
        assertTrue(cache.containsKey(0));
        assertEquals(cache.get(0), 5);
    }
}