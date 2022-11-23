package main.java.v1.util;

import com.sun.security.jgss.GSSUtil;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class TextSequence {
    public String text;
    private int index;
    private HashMap<String, String> map;
    private StringBuilder bob;

    public TextSequence(String _text) {
        this.text = _text;
        this.index = 0;
        this.map = new HashMap<String, String>();
        this.bob = new StringBuilder();
    }

    public String current() {
        if (this.index < this.text.length()) {
            return toString(text.charAt(index));
        } else {
            return "\0";
        }
    }

    public String previous() {
        return toString(text.charAt(index - 1));
    }

    public String next() {
        this.index++;
        if (this.index < this.text.length()) {
            return toString(text.charAt(this.index));
        } else {
            return "\0";
        }
    }

    public String next(int offset) {
        while (offset >= 0) {
            this.index++;
            offset--;
        }
        if (this.index < this.text.length()) {
            return toString(text.charAt(this.index));
        } else {
            return "\0";
        }
    }

    public String peek() {
        if (index + 1 < text.length()) {
            return toString(text.charAt(index + 1));
        } else {
            return "\0";
        }
    }

    private final static Character VARIABLE_DELIMITER_START = '<';
    private final static Character VARIABLE_DELIMITER_END = '>';

    public int isKeyword() {
        if (!Character.isLetter(text.charAt(index))) return 0;

        bob = new StringBuilder();
        StringBuilder bobType = new StringBuilder();

        int startIndex = index;

        while (!isWhiteSpaceOrEOF(current())) {
            bob.append(current());
            next();
        }

        String word = bob.toString();
        Token keyword = Token.EOF;

        try {
            if (word.toLowerCase().contains("variable") &&
                    word.charAt(word.length() - 1) == VARIABLE_DELIMITER_END
            ) {
                List<String> types = getTypes(new ArrayList<>(), word);
                /*
                 get(0) is always "variable"
                 get(1) will get the type of the variable at level = 1
                 This design is so as to make the variable scalable
                 For eg. in future versions the language needs -> variable<array<array<num>>>
                */
                if (types.get(1).equalsIgnoreCase("number")) {
                    keyword = Token.VARIABLE_NUM;
                } else if (types.get(1).equalsIgnoreCase("string")) {
                    keyword = Token.VARIABLE_STR;
                } else if (types.get(1).equalsIgnoreCase("any")) {
                    keyword = Token.VARIABLE_ANY;
                } else {
                    index = startIndex;
                    abort(String.format("LexingError: Unknown character '%s' found at index '%d'", bob.toString(), this.getIndex()));
                }
                return keyword.getId();
            } else if (word.equalsIgnoreCase("if")) {
                keyword = Token.IF;
            } else if (word.equalsIgnoreCase("elif")) {
                keyword = Token.ELIF;
            } else if (word.equalsIgnoreCase("else")) {
                keyword = Token.ELSE;
            } else if (word.equalsIgnoreCase("print")) {
                keyword = Token.PRINT;
            } else if (word.equalsIgnoreCase("repeat")) {
                keyword = Token.REPEAT;
            } else if (word.equalsIgnoreCase("function")) {
                keyword = Token.FUNCTION;
            } else {
                if (!Character.isDigit(word.charAt(0))) {
                    keyword = Token.IDENTIFIER;
                    return keyword.getId();
                }
//                index = startIndex;
//                abort(String.format("LexingError: Unknown character '%s' found at index '%d'", word, this.getIndex()));
            }

            return keyword.getId();
        } catch (Exception e) {
            abort(String.format("LexingError: Unknown character '%s' found at index '%d'", word, this.getIndex()));
        }
        return keyword.getId();
    }

    private static List<String> getTypes(List<String> _list, String type) {
        if (containsDelimiter(type, VARIABLE_DELIMITER_START) == -1 && containsDelimiter(type, VARIABLE_DELIMITER_END) == -1) {
            _list.add(type);
            return _list;
        }
        if (containsDelimiter(type, VARIABLE_DELIMITER_START) == -1 || containsDelimiter(type, VARIABLE_DELIMITER_END) == -1) {
            System.exit(1);
        }

        String tmp = "";

        int i = 0;
        while (type.charAt(i) != VARIABLE_DELIMITER_START) {
            tmp += String.valueOf(type.charAt(i));
            i++;
        }

        _list.add(tmp);
        return getTypes(_list, type.substring(i + 1, type.length() - 1));
    }


    private static int containsDelimiter(String s, Character delimiter) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == delimiter) return i;
        }
        return -1;
    }

    public String skipComment() {
        StringBuilder commentContent = new StringBuilder();

        int startIndex = this.index;

        this.next(1);
        while (!this.current().equals("-")) {
            if (current().equals("\0")) {
                index = startIndex;
                abort(String.format("LexingError: Unknown character '%s' found at index '%d'", this.current(), this.getIndex()));
            }
            commentContent.append(this.current());
            this.next();
        }
        this.next();
        if (this.current().equals("-")) {
            this.next();
        }
        else {
            index = startIndex;
            abort(String.format("LexingError: Unknown character '%s' found at index '%d'", this.current(), this.getIndex()));
        }

        return commentContent.toString();
    }

    public String handleString() {
        StringBuilder stringContent = new StringBuilder();

        int startIndex = index;
        this.next();
        while (!this.current().equals("\"")) {
            if (current().equals("\0")) {
                index = startIndex;
                abort(String.format("LexingError: Unknown character '%s' found at index '%d'", this.current(), this.getIndex()));
            }

            stringContent.append(this.current());
            this.next();
        }
//        System.out.println(stringContent);
        return stringContent.toString();
    }

    public void skipWhitespace() {
        if (isWhiteSpaceOrEOF(current())) {
            while (isWhiteSpaceOrEOF(peek())
            ) {
                this.index++;
            }
            this.next();
        }
    }

    private boolean isWhiteSpace(String c) {
        return c.equals(" ") ||
                c.equals("\t") ||
                c.equals("\r") ||
                c.equals("\f");
    }

    private boolean isWhiteSpaceOrEOF(String c) {
        return isWhiteSpace(c) ||
                c.equals("\0");
    }

    public void abort() {
        System.exit(1);
    }

    public void abort(String message) {
        System.out.println(message);
        System.exit(1);
    }

    public boolean hasNext() {
        return !this.current().equals("\0");
    }

    public int getIndex() {
        return this.index;
    }

    private static String toString(Character c) {
        return String.valueOf(c);
    }

    public static Character toChar(String s) {
        if (s.length() == 1) {
            return s.charAt(0);
        }

        return '\0';
    }
}
