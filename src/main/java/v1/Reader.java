package main.java.v1;

import main.java.v1.util.TextSequence;
import main.java.v1.util.Token;

import java.util.List;
import java.util.ArrayList;

public class Reader implements Compile {

    private final TextSequence ts;
    private final StringBuilder bob;
    public List<Token> tokenList;

    public Reader(String text) {
        this.ts = new TextSequence(text);
        this.bob = new StringBuilder();
        this.tokenList = new ArrayList<>();
    }


    public void start() {
        this.read();
        System.out.println("Lexing finished successfully!");
    }

    public void debug() {
        System.out.println("Debugging in Reader.");
    }

    private void read() {
        Token token;
        while (ts.hasNext()) {
            ts.skipWhitespace();

            int keywordId = ts.isKeyword();

            if (ts.current().equals("+")) {
                token = Token.PLUS;
            } else if (ts.current().equals("=")) {
                if (ts.peek().equals("=")) {
                    token = Token.EQ;
                    ts.next();
                } else {
                    token = Token.ASSIGNMENT;
                }
            } else if (ts.current().equals("!")) {
                if (ts.peek().equals("=")) {
                    token = Token.NTEQ;
                    ts.next();
                } else {
                    token = Token.EOF;
                    ts.abort(String.format("LexingError: Unknown character '%s' found at index '%d'", ts.current(), ts.getIndex()));
                }
            } else if (ts.current().equals("-")) {
                if (ts.peek().equals("-")) {
                    ts.skipComment();
                    token = Token.COMMENT;
                } else {
                    token = Token.MINUS;
                }
            } else if (ts.current().equals("/")) {
                token = Token.DIVIDE;
            } else if (ts.current().equals("%")) {
                token = Token.MODULO;
            } else if (ts.current().equals("_")) {
                token = Token.UNDERSCORE;
            }
            // If keyword is found, find out which one is it
            else if (keywordId != 0) {
                token = switch (keywordId) {
                    case 300 -> Token.NOTHING;
                    case 301 -> Token.IF;
                    case 302 -> Token.ELIF;
                    case 303 -> Token.ELSE;
                    case 304 -> Token.PRINT;
                    case 305 -> Token.REPEAT;
                    case 350 -> Token.FUNCTION;
                    case 351 -> Token.VARIABLE_NUM;
                    case 352 -> Token.VARIABLE_STR;
                    case 353 -> Token.VARIABLE_ANY;
                    case 360 -> Token.IDENTIFIER;
                    default -> Token.EOF;
                };
            } else {
                token = Token.EOF;
                ts.abort(String.format("LexingError: Unknown character '%s' found at index '%d'", ts.current(), ts.getIndex()));
            }

            tokenList.add(token);
            ts.next();
        }
    }
}
