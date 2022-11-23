package main.java.v1;

import main.java.v1.util.Token;
import main.java.v1.util.TokenSequence;

import java.util.List;
import java.util.Locale;

public class Parser implements Compile {
    private final TokenSequence ts;

    public Parser(List<Token> _tokenList) {
        this.ts = new TokenSequence(_tokenList);
    }

    public void debug() {
        System.out.println("Debugging in Parser.");
    }

    public void start() {
        this.parse();
        System.out.println("Parsing finished successfully!");
    }

    private void parse() {
        program();
    }

    private void program() {
        System.out.println("PROGRAM:");

        while (!ts.verifyCurrent(Token.EOF)) {
//            System.out.println(ts.current());
            statement();
        }
    }

    private void statement() {
        if (ts.verifyCurrent(Token.PRINT)) {
            System.out.println("STATEMENT-PRINT");
            ts.next();

            if (ts.verifyCurrent(Token.STRING)) {
                ts.next();
            } else {
                expression();
            }
        }

        this.handleNewLineOrEOF();

    }

    private void expression() {
        System.out.println("EXPRESSION");
        // Might call something else
        // Hence, creating an implicit tree
    }


    private void handleNewLineOrEOF() {
        if (ts.verifyCurrent(Token.EOF)) {
            System.out.println("EOF");
            ts.match(Token.EOF);
        } else {
            System.out.println("NEWLINE");
            ts.match(Token.NEWLINE);
            while (ts.verifyCurrent(Token.NEWLINE)) {
                ts.next();
            }
        }


    }

}
