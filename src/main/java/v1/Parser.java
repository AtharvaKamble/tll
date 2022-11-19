package main.java.v1;

import main.java.v1.util.Token;
import main.java.v1.util.TokenSequence;

import java.util.List;

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

    }

    private void program() {
        statement();
    }

    private void statement() {
        expression();
    }

    private void expression() {
        // Might call something else
        // Hence, creating an implicit tree
    }


}
