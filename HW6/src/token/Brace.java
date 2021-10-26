package token;

import visitor.TokenVisitor;

public interface Brace extends Token {
    @Override
    default void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
