package token;

public class Divide implements Operation {
    @Override
    public int priority() {
        return 0;
    }

    @Override
    public int calc(int a, int b) {
        return a / b;
    }

    @Override
    public String toString() {
        return "/";
    }
}
