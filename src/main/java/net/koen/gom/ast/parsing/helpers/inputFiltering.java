package net.koen.gom.ast.parsing.helpers;

import net.koen.gom.lexing.Token;
import net.koen.gom.lexing.TokenType;

import java.util.ArrayList;
import java.util.List;

public class inputFiltering {
    public static List<Token> consumeUntilBang(List<Token> tokens, int start) {
        int j = start;
        List<Token> output = new ArrayList<>();
        while (j < tokens.size() && tokens.get(j).type != TokenType.BANG && tokens.get(j).type != TokenType.QUESTION) {
            Token t = tokens.get(j);
            output.add(t);
            j++;
        }
        return output;
    }

    public static List<Token> filterSpaces(List<Token> input) {
        List<Token> filtered = input.stream()
                .filter(t -> t.type != TokenType.SPACE)
                .toList();
        return filtered;
    }
}
