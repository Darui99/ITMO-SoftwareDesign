package token;

public class Add implements Operation {
    @Override
    public int priority() {
        return 1;
    }

    @Override
    public int calc(int a, int b) {
        return a + b;
    }

    @Override
    public String toString() {
        return "+";
    }
}
