package net.koen.gom.lexing;

public class Token {
    public final TokenType type;  // Type of the token
    public final String lexeme;   // The raw text (e.g., "if", "(", "123")
    public final Object literal;  // For numbers, strings, etc.
    public final int position;    // Where it appeared
    public int leadingSpaces;     // For arithmetic operations, to set the order

    // Constructor method
    public Token(TokenType type, String lexeme, Object literal, int position) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.position = position;
    }

    // Method to parse the token to a String, example output: KEYWORD if null
    @Override
    public String toString() {
        return type + " " + lexeme + " " + literal;
    }

    public void setLeadingSpaces(int value) {
        this.leadingSpaces = value;
    }
}
