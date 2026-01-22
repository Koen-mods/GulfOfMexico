package net.koen.gom.ast.parsing.controlflow;

import net.koen.gom.ast.nodes.Node;
import net.koen.gom.ast.nodes.booleanexpressions.BooleanExpression;
import net.koen.gom.ast.nodes.booleanexpressions.ComparatorExpression;
import net.koen.gom.ast.nodes.booleanexpressions.LiteralExpression;
import net.koen.gom.ast.parsing.helpers.inputFiltering;
import net.koen.gom.lexing.Token;
import net.koen.gom.lexing.TokenType;

import java.util.List;
import java.util.Set;

public class IfStatementParser {
    public int Parse(List<Token> input, int position, List<Node> output) {
        Node outputnode;
        List<Token> inputFP = input.subList(position, input.size() - 1);
        List<Token> inputLB = inputFiltering.consumeUntilLBrace(inputFP, 0); // Filter to if (expression) {
        List<Token> usable = inputFiltering.filterSpaces(inputLB); // if(expression){

        int pos = 1;
        Token curr = input.get(pos);

        if (curr.type != TokenType.LPAREN) {
            System.out.println("What the f- did you do to that if statement? It's missing a '('! On line " + curr.line + "!");
            return -1;
        }

        // Lparen check [x]

        pos++;
        curr = usable.get(pos);

        BooleanExpression expression = null;

        switch (curr.type) {
            case KEYWORD -> {
                if (!(curr.lexeme.equals("false")) && !(curr.lexeme.equals("true"))) {
                    System.out.println("Why did you put '" + curr.lexeme + "' here? That errors! *sigh* line " + curr.line);
                    return -1;
                } else if (curr.lexeme.equals("true")) {
                    expression = new LiteralExpression(true);
                } else {
                    expression = new LiteralExpression(false);
                }
            } case NUMBER -> {
                float num1 = (float) curr.literal;
                pos++;
                curr = usable.get(pos);

                if (curr.type != TokenType.OPERATOR) {
                    Set<String> validOps = Set.of("=", "<", ">", ";");
                    if (!validOps.contains(curr.lexeme)) {
                        System.out.println("Yes this is in operator but you can't use '" + curr.lexeme + "' for comparison! Line " + curr.line);
                        return -1;
                    }

                    Token next = usable.get(pos + 1);
                    if (next.type != TokenType.OPERATOR && (!curr.lexeme.equals(">") && !curr.lexeme.equals("<"))) {
                        System.out.println("You can't use just '" + curr.lexeme + "' in a comparison, you need more you know! Line " + curr.lexeme);
                        return -1;
                    }

                    String comparator = curr.lexeme;

                    pos++;
                    curr = usable.get(pos);

                    if (curr.type == TokenType.OPERATOR) {
                        if (!curr.lexeme.equals("=")) {
                            System.out.println("That's.. not how you make a comparison... line " + curr.line);
                            return -1;
                        }

                        comparator += "=";
                    }

                    pos++;
                    curr = usable.get(pos);

                    if (curr.type != TokenType.NUMBER) {
                        System.out.println("You.. you can't compare that! Line " + curr.line);
                        return -1;
                    }

                    float num2 = (float) curr.literal;

                    expression = new ComparatorExpression<Float>(num1, comparator, num2); // if (1 == 1...
                }
            } case STRING -> {
                String str1 = (String) curr.literal;
                pos++;
                curr = usable.get(pos);

                if (curr.type != TokenType.OPERATOR) {
                    Set<String> validOps = Set.of("=", ";");
                    if (!validOps.contains(curr.lexeme)) {
                        System.out.println("Yes this is in operator but you can't use '" + curr.lexeme + "' for comparison! Line " + curr.line);
                        return -1;
                    }

                    Token next = usable.get(pos + 1);
                    if (next.type != TokenType.OPERATOR) {
                        System.out.println("You can't use just '" + curr.lexeme + "' in a comparison, you need more you know! Line " + curr.lexeme);
                        return -1;
                    }

                    String comparator = curr.lexeme;

                    pos++;
                    curr = usable.get(pos);

                    if (curr.type == TokenType.OPERATOR) {
                        if (!curr.lexeme.equals("=")) {
                            System.out.println("That's.. not how you make a comparison... line " + curr.line);
                            return -1;
                        }

                        comparator += "=";
                    }

                    pos++;
                    curr = usable.get(pos);

                    if (curr.type != TokenType.STRING) {
                        System.out.println("You.. you can't compare that! Line " + curr.line);
                        return -1;
                    }

                    java.lang.String str2 = (String) curr.literal;

                    expression = new ComparatorExpression<String>(str1, comparator, str2); // if (1 == 1...
                }
            }
            case null, default -> {
                System.out.println("No no no no no, this does NOT belong here! Line " + curr.line);
            }
        }

        return -1;
    }
}
