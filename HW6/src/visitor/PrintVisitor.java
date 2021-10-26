package visitor;

import token.Brace;
import token.Number;
import token.Operation;
import token.Token;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class PrintVisitor implements TokenVisitor {
    private final OutputStream output;

    public PrintVisitor(final OutputStream output) {
        this.output = output;
    }

    private void write(Token token) {
        try {
            output.write((token.toString() + " ").getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void visit(Number token) {
        write(token);
    }

    @Override
    public void visit(Brace token) {
        write(token);
    }

    @Override
    public void visit(Operation token) {
        write(token);
    }
}
