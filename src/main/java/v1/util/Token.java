package main.java.v1.util;

public enum Token {
    EOF(0),
    NUMBER(1),
    STRING(2),
    COMMENT(3),
    UNDERSCORE(4),

    // Operators
    PLUS(200),
    MINUS(201),
    DIVIDE(202),
    MODULO(203),
    MULTIPLY(204),
    ASSIGNMENT(205),
    EQ(206),
    NTEQ(207),
    GTEQ(208),
    LTEQ(209),


    // Keywords
    NOTHING(300),
    IF(301),
    ELIF(302),
    ELSE(303),
    PRINT(304),
    REPEAT(305),

    FUNCTION(350),
    VARIABLE_NUM(351),
    VARIABLE_STR(352),
    VARIABLE_ANY(353),
    IDENTIFIER(360);

    private final int id;

    Token(int _id) {
        this.id = _id;
    }

    public int getId() {
        return this.id;
    }

}
