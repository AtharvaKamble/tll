package main.java.v1.util;

import java.util.List;

public class TokenSequence {
    private final List<Token> tokenList;
    private int index;

    public TokenSequence (List<Token> _tokenList) {
        this.index = 0;
        this.tokenList = _tokenList;
    }

    public Token current() {
        return tokenList.get(index);
    }

    public void next() {
        index++;
    }

    public Token peek() {
        if (index + 1 > tokenList.size()) {
            return tokenList.get(index+1);
        } else {
            return Token.EOF;
        }
    }

    public boolean hasNext() {
        return index <= tokenList.size()-1;
    }

    public void abort () {
        System.out.printf("ParsingError: %s", tokenList.get(index));
        System.exit(1);
    }

    public int getIndex() {
        return index;
    }

}
