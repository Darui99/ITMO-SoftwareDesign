package visitor;

import token.Brace;
import token.Number;
import token.Operation;

import java.util.Stack;

public class CalcVisitor implements TokenVisitor {
    private final Stack<Integer> stack;

    public CalcVisitor() {
        stack = new Stack<>();
    }

    @Override
    public void visit(Number token) {
        stack.push(token.getVal());
    }

    @Override
    public void visit(Brace token) {
        throw new RuntimeException("Calculation error: unexpected token");
    }

    @Override
    public void visit(Operation token) {
        if (stack.size() < 2) {
            throw new RuntimeException("Calculation error: missed operands");
        }
        int secondArg = stack.pop();
        int firstArg = stack.pop();
        stack.push(token.calc(firstArg, secondArg));
    }

    public int getResult() {
        if (stack.size() != 1) {
            throw new RuntimeException("Calculation error: missed operations");
        }
        return stack.pop();
    }
}
