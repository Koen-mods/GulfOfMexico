package net.koen.gom.lexing;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Lexer {
    private static final Set<Character> OPERATORS = Set.of(
            '+', '-', '*', '/', '=', '<', '>', ';', '%', '&', '|', '^', ':', '$', '€'
    );
    private static final Set<Character> STRING_SIGNS = Set.of(
            '\'', '\"'
    );

    public static List<Token> Lex(String input) {
        List<Token> output = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);
            if (current == '(') {
                Token token = new Token(TokenType.LPAREN, "(", null, i);
                output.add(token);
            } else if (current == ')') {
                Token token = new Token(TokenType.RPAREN, ")", null, i);
                output.add(token);
            } else if (current == '{') {
                Token token = new Token(TokenType.LBRACE, "{", null, i);
                output.add(token);
            } else if (current == '}') {
                Token token = new Token(TokenType.RBRACE, "}", null, i);
                output.add(token);
            } else if (Character.isDigit(current)) {
                char[] chars = input.toCharArray();
                int begin = i;
                int end = input.length();
                for (int j = i; j < input.length(); j++) {
                    if (!Character.isDigit(chars[j]) && chars[j] != '.') {
                        end = j;
                        i = j - 1;
                        break;
                    } else {
                        continue;
                    }
                }
                String number = input.substring(begin, end);
                float literal = Float.parseFloat(number);
                Token token = new Token(TokenType.NUMBER, number, literal, i);
                output.add(token);
            } else if (Character.isLetter(current)) {
                char[] chars = input.toCharArray();
                int begin = i;
                int end = input.length();
                Token token = null;
                boolean isKeyword = false;
                for (int j = i; j < input.length(); j++) {
                    if (chars[j] == '(' || Character.isWhitespace(chars[j]) || OPERATORS.contains(chars[j]) || STRING_SIGNS.contains(chars[j]) || chars[j] == '!') {
                        end = j;
                        break;
                    }
                }
                String word = input.substring(begin, end);
                for (Keyword k : Keyword.values()) {
                    if (word.equals(k.value)) {
                        token = new Token(TokenType.KEYWORD, k.value, null, i);
                        isKeyword = true;
                        break;
                    }
                }
                if (!isKeyword) {
                    token = new Token(TokenType.IDENTIFIER, word, null, i);
                }
                output.add(token);
                i = end - 1;
            } else if (OPERATORS.contains(current)) {
                Token token = new Token(TokenType.OPERATOR, Character.toString(current), null, i);
                output.add(token);
            } else if (STRING_SIGNS.contains(current)) {
                char sign = current;
                int begin = i;
                int end = input.length();
                char[] chars = input.toCharArray();
                for (int j = i; j < input.length(); j++) {
                    if (sign == chars[j]) {
                        end = j;
                    }
                }
                String word = input.substring(begin, end + 1);
                String wordNoQuotes = input.substring(begin + 1, end);
                Token token = new Token(TokenType.STRING, word, wordNoQuotes, i);
                output.add(token);
                i = end;
            } else if (current == '!') {
                Token token = new Token(TokenType.BANG, "!", null, i);
                output.add(token);
            } else if (current == ',') {
                Token token = new Token(TokenType.COMMA, ",", null, i);
                output.add(token);
            } else if (current == ' ') {
                    int start = i;
                    while (i + 1 < input.length() && input.charAt(i + 1) == ' ') {
                        i++;
                    }
                    int length = i - start + 1;
                    Token token = new Token(TokenType.SPACE, " ".repeat(length), length, start);
                    output.add(token);
                } else if (current == '\n') {
                Token token = new Token(TokenType.NEWLINE, "\\n", null, i);
                output.add(token);
            } else if (current == '.') {
                boolean partOfNumber = false;
                if (i > 0 && Character.isDigit(input.charAt(i - 1))) {
                    partOfNumber = true;
                }

                if (!partOfNumber) {
                    Token token = new Token(TokenType.DOT, ".", null, i);
                    output.add(token);
                }
            } else if (current == '?') {
                Token token = new Token(TokenType.QUESTION, "?", null, i);
                output.add(token);
            }
        }
        Token EOFToken = new Token(TokenType.EOF, "EOF", null, input.length() + 1);
        output.add(EOFToken);
        return output;
    }
}
