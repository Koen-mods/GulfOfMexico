package net.koen.gom.ast.parsing.variables;

import net.koen.gom.ast.nodes.Node;
import net.koen.gom.ast.nodes.VariableEdit;
import net.koen.gom.ast.parsing.Parser;
import net.koen.gom.ast.parsing.helpers.inputFiltering;
import net.koen.gom.execution.variables.Variable;
import net.koen.gom.execution.variables.VariableLevel;
import net.koen.gom.execution.variables.VariableSystem;
import net.koen.gom.execution.variables.VariableType;
import net.koen.gom.lexing.Token;
import net.koen.gom.lexing.TokenType;

import java.util.List;

public class EditParser {
    public static int Parse(List<Token> input, int start, String word, List<Node> output) {
        Node outputNode;
        Object value = null;
        VariableType type = null;
        List<Token> statement = inputFiltering.consumeUntilBang(input, start);
        int length = statement.size();
        statement = inputFiltering.filterSpaces(statement);
        List<TokenType> acceptedTypes = List.of(TokenType.STRING, TokenType.NUMBER, TokenType.KEYWORD, TokenType.IDENTIFIER);
        if (statement.size() == 3) { // var(0) =(1) "value"(2)!
            if (statement.get(0).type == TokenType.IDENTIFIER && statement.get(1).lexeme.equals("=") && acceptedTypes.contains(statement.get(2).type)) {
                Variable<?> var = Parser.findTempVar(word);
                if (var == null) {
                    System.out.println("I don't know what you're trying to do here, but that variable (" + word + ") doesn't exist. Define it first, will ya?");
                    return -1;
                } else {
                    VariableLevel level = var.getLevel();
                    switch (level) {
                        case VARVAR, VARCONST -> {

                        } case CONSTVAR -> {
                            System.out.println("Did you not read the docs? Constant variables can't be reassigned...");
                            return -1;
                        }
                        case CONSTCONST -> {
                            System.out.println("Don't ever touch constant constants...");
                            return -1;
                        } case null, default -> {
                            System.out.println("If you see this, please reach out to me or make a github issue. This is not meant to be possible.");
                            return -1;
                        }
                    }
                    Token val = statement.get(2);
                    switch (val.type) {
                        case STRING -> {
                            type = VariableType.STRING;
                            value = val.literal;
                        } case NUMBER -> {
                            type = VariableType.NUMBER;
                            value = val.literal;
                        } case KEYWORD -> {
                            if (val.lexeme.equals("true")) {
                                type = VariableType.BOOLEAN;
                                value = true;
                            } else if (val.lexeme.equals("false")) {
                                type = VariableType.BOOLEAN;
                                value = false;
                            } else {
                                System.out.println("Did you seriously just use a keyword as variable value? Oh come on...");
                                return -1;
                            }
                        } case IDENTIFIER -> {
                            Variable<?> varFound = Parser.findTempVar(val.lexeme);
                            if (varFound != null) {
                                type = varFound.getType();
                                value = varFound.getValue();
                            } else {
                                System.out.println("That didn't return anything, so this value ain't shit");
                                return -1;
                            }
                        }
                    }
                    assert value != null;
                    assert type != null;
                    switch (type) {
                        case STRING -> {
                            outputNode = new VariableEdit<String>(var.getName(), (String) value);
                            output.add(outputNode);
                            return start + length;
                        } case NUMBER -> {
                            outputNode = new VariableEdit<Float>(var.getName(), (Float) value);
                            output.add(outputNode);
                            return start + length;
                        } case BOOLEAN -> {
                            outputNode = new VariableEdit<Boolean>(var.getName(), (Boolean) value);
                            output.add(outputNode);
                            return start + length;
                        } case null, default -> {
                            System.out.println("If you see this, please reach out to me or make a github issue. This is not meant to be possible.");
                            return -1;
                        }
                    }
                }
            } else {
                System.out.println("Incomplete statement... *sigh*");
                return -1;
            }
        } else {
            System.out.println("Incomplete statement... *sigh*");
            return -1;
        }
    }
}
