package visitor;

import token.Number;
import token.Brace;
import token.Operation;

public interface TokenVisitor {
    void visit(Number token);

    void visit(Brace token);

    void visit(Operation token);
}
