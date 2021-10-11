import java.util.HashMap;
import java.util.Map;

public class DoublyLinkedList<K, V> {
    class Node {
        Node left;
        Node right;
        final K key;
        V val;

        Node(final K key, final V val) {
            this.left = null;
            this.right = null;
            this.key = key;
            this.val = val;
        }

        K remove() {
            assert (key != null);
            if (left != null) {
                left.right = right;
            }
            if (right != null) {
                right.left = left;
            }
            left = null;
            right = null;
            return key;
        }

        V getVal() {
            assert (val != null);
            return val;
        }

        void setVal(final V val) {
            this.val = val;
        }
    }

    private Node top;
    private Node bot;
    Map<K, Node> data;

    public DoublyLinkedList() {
        top = null;
        bot = null;
        data = new HashMap<>();
    }

    private void insertToBot(final Node v) {
        if (top == null) {
            top = v;
            bot = v;
            return;
        }

        bot.right = v;
        v.left = bot;
        bot = v;
    }

    public void addToBot(final K key, final V val) {
        final int size = size();
        Node node = new Node(key, val);
        insertToBot(node);
        data.put(key, node);
        assert (size() == size + 1);
    }


    private K eraseTop() {
        assert(top != null);
        Node nTop = top.right;
        K res = top.remove();
        top = nTop;
        assert (top == null || top.left == null);
        return res;
    }

    private K eraseBot() {
        assert(bot != null);
        Node nBot = bot.left;
        K res = bot.remove();
        bot = nBot;
        assert (bot == null || bot.right == null);
        return res;
    }

    private K erase(final Node v) {
        if (v == top) {
            return eraseTop();
        } else {
            if (v == bot) {
                return eraseBot();
            }
            else {
                return v.remove();
            }
        }
    }

    public void removeTop() {
        final int size = size();
        K res = eraseTop();
        data.remove(res);
        assert (size() == size - 1);
    }

    public void moveToBot(final K key) {
        assert(data.containsKey(key));
        final int size = size();
        erase(data.get(key));
        assert (size() == size);
        insertToBot(data.get(key));
        assert(size() == size);
    }

    public V get(final K key) {
        return data.get(key).getVal();
    }

    public void set(final K key, final V val) {
        data.get(key).setVal(val);
    }

    public boolean containsKey(final K key) {
        return data.containsKey(key);
    }

    public int size() {
        return data.size();
    }
}
