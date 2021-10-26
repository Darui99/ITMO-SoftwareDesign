package token;

import visitor.TokenVisitor;

public class Number implements Token {
    private final int val;

    public Number(int val) {
        this.val = val;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    public int getVal() {
        return val;
    }

    @Override
    public String toString() {
        return Integer.toString(val);
    }
}
