package main.java.v1.util;

import java.util.List;

public class TokenSequence {
    private final List<Token> tokenList;
    private int index;

    public TokenSequence(List<Token> _tokenList) {
        this.index = 0;
        this.tokenList = _tokenList;
    }

    public Token current() {
        if (this.index < this.tokenList.size()) {
            return tokenList.get(index);
        } else {
            return Token.EOF;
        }
    }

    public Token next() {
        this.index++;
        if (this.index < this.tokenList.size()) {
            return tokenList.get(this.index);
        } else {
            return Token.EOF;
        }
    }

    public Token peek() {
        if (index + 1 > tokenList.size()) {
            return tokenList.get(index + 1);
        } else {
            return Token.EOF;
        }
    }

    public boolean hasNext() {
        return index <= tokenList.size() - 1;
    }

    public boolean verifyCurrent(Token token) {
        return token == current();
    }

    public boolean verifyPeek(Token token) {
        return token == peek();
    }

    public void abort() {
        System.out.printf("ParsingError: %s", tokenList.get(index));
        System.exit(1);
    }

    public int getIndex() {
        return index;
    }

}
