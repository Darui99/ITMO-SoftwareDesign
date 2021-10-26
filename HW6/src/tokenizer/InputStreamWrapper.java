package tokenizer;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWrapper {
    private final InputStream inputStream;
    private char current;
    private boolean needBack;

    public InputStreamWrapper(final InputStream inputStream) {
        this.inputStream = inputStream;
        needBack = false;
    }

    public char getCurrent() {
        return current;
    }

    public char getNext() {
        if (needBack) {
            needBack = false;
            return current;
        }
        try {
            current = (char) inputStream.read();
            return current;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void back() {
        needBack = true;
    }
}
