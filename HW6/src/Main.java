import token.Token;
import tokenizer.Tokenizer;
import visitor.CalcVisitor;
import visitor.ParserVisitor;
import visitor.PrintVisitor;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Tokenizer tokenizer = new Tokenizer(System.in);

            List<Token> tokens = tokenizer.tokenize();

            ParserVisitor parserVisitor = new ParserVisitor();
            tokens.forEach(t -> t.accept(parserVisitor));
            List<Token> parsed = parserVisitor.getParsed();

            PrintVisitor printVisitor = new PrintVisitor(System.out);
            System.out.print("Expression in polish notation: ");
            parsed.forEach(t -> t.accept(printVisitor));

            CalcVisitor calcVisitor = new CalcVisitor();
            parsed.forEach(t -> t.accept(calcVisitor));
            System.out.println("\nCalculated expression result: " + calcVisitor.getResult());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
