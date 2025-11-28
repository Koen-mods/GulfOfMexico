package net.koen.gom.ast.parsing.variables;

import net.koen.gom.ast.nodes.Node;
import net.koen.gom.ast.nodes.VariableAssignment;
import net.koen.gom.ast.nodes.arithmetic.*;
import net.koen.gom.ast.parsing.Parser;
import net.koen.gom.ast.parsing.helpers.inputFiltering;
import net.koen.gom.execution.variables.Variable;
import net.koen.gom.execution.variables.VariableLevel;
import net.koen.gom.execution.variables.VariableType;
import net.koen.gom.lexing.Token;
import net.koen.gom.lexing.TokenType;

import java.util.List;

public class AssignmentParser {
    public static int parse(List<Token> input, int i, String word, List<Node> output) {
        Token token = input.get(i);
        VariableLevel level = null;
        VariableType type = null;
        String name = null;
        Object value = null;
        boolean var = false;
        List<Token> untilBang = inputFiltering.consumeUntilBang(input, i);
        List<Token> filtered = inputFiltering.filterSpaces(untilBang);
        if (word.equals("var")) {
            var = true;
        }
        if (filtered.size() > 1) {
            Token nextTok = filtered.get(1);
            switch (nextTok.lexeme) {
                case "var" -> {
                    if (var) {
                        level = VariableLevel.VARVAR;
                    } else {
                        level = VariableLevel.CONSTVAR;
                    }
                } case "const" -> {
                    if (var) {
                        level = VariableLevel.VARCONST;
                    } else {
                        level = VariableLevel.CONSTCONST;
                    }
                } case null, default -> {
                    System.out.println("DUUUUUUUUUDE, incorrect variable assignment! The " + (token.position + 1) + "th character in your program!");
                    return -1;
                }
            }
            if (filtered.size() == 5) { // example: var(0) var(1) variable(2) =(3) "hi"(4)
                if (filtered.get(2).type != TokenType.IDENTIFIER) {
                    System.out.println("Incomplete statement or invalid name... *sigh*");
                    return -1;
                } else {
                    name = filtered.get(2).lexeme;
                }
                if (!filtered.get(3).lexeme.equals("=")) {
                    System.out.println("Incomplete statement... *sigh*");
                    return -1;
                }
                Token val = filtered.get(4);
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
            } else if (filtered.size() == 7) { // var(0) var(1) variable(2) =(3) 1(4) +(5) 1(6)
                if (filtered.get(2).type != TokenType.IDENTIFIER) {
                    System.out.println("Incomplete statement or invalid name... *sigh*");
                    return -1;
                } else {
                    name = filtered.get(2).lexeme;
                }
                if (!filtered.get(3).lexeme.equals("=")) {
                    System.out.println("Incomplete statement... *sigh*");
                    return -1;
                }
                Token val = filtered.get(4);
                switch (val.type) {
                    case STRING -> {
                        System.out.println("What were you gonna do with that string? You can't add them or anything");
                        return -1;
                    } case NUMBER -> {
                        type = VariableType.NUMBER;
                        Token next = filtered.get(5);
                        if (next.type != TokenType.OPERATOR) {
                            System.out.println("Do you even know how to code? AN OPERATOR BELONGS HERE!!!!");
                            return -1;
                        } else if (next.lexeme.equals("=") || next.lexeme.equals("<") || next.lexeme.equals(">") || next.lexeme.equals(";") || next.lexeme.equals("&") || next.lexeme.equals("|") || next.lexeme.equals(":") || next.lexeme.equals("$") || next.lexeme.equals("€")) { // '+', '-', '*', '/', '=', '<', '>', ';', '%', '&', '|', '^', ':', '$', '€'
                            System.out.println("Jeez. Make your comparators or assignments or whatnot somewhere else");
                            return -1;
                        } else if (filtered.get(6).type != TokenType.NUMBER && filtered.get(6).type != TokenType.IDENTIFIER) {
                            System.out.println("Seriously? Now? You were almost done with the arithmetic operation and you say '" + filtered.get(6).lexeme + "'");
                            return -1;
                        } else {
                            Token secondVal = filtered.get(6);
                            switch (secondVal.type) {
                                case NUMBER -> {
                                    switch (next.lexeme) {
                                        case "+" -> {
                                            IncrementOperation op = new IncrementOperation((Float) val.literal, (Float) secondVal.literal);
                                            value = op.eval();
                                        } case "-" -> {
                                            DecrementOperation op = new DecrementOperation((Float) val.literal, (Float) secondVal.literal);
                                            value = op.eval();
                                        } case "*" -> {
                                            MultiplyOperation op = new MultiplyOperation((Float) val.literal, (Float) secondVal.literal);
                                            value = op.eval();
                                        } case "/" -> {
                                            DivideOperation op = new DivideOperation((Float) val.literal, (Float) secondVal.literal);
                                            value = op.eval();
                                        } case "%" -> {
                                            ModulusOperation op = new ModulusOperation((Float) val.literal, (Float) secondVal.literal);
                                            value = op.eval();
                                        } case "^" -> {
                                            PowerOperation op = new PowerOperation((Float) val.literal, (Float) secondVal.literal);
                                            value = op.eval();
                                        } case null, default -> {
                                            System.out.println("How did you even do this?");
                                            return -1;
                                        }
                                    }
                                } case IDENTIFIER -> {
                                    Variable<?> varFound = Parser.findTempVar(secondVal.lexeme);
                                    if (varFound == null) {
                                        System.out.println("What is that identifier '" + secondVal.lexeme + "' supposed to be?");
                                        return -1;
                                    }
                                    Float secondValue = (Float) varFound.getValue();
                                    switch (next.lexeme) {
                                        case "+" -> {
                                            IncrementOperation op = new IncrementOperation((Float) val.literal, secondValue);
                                            value = op.eval();
                                        } case "-" -> {
                                            DecrementOperation op = new DecrementOperation((Float) val.literal, secondValue);
                                            value = op.eval();
                                        } case "*" -> {
                                            MultiplyOperation op = new MultiplyOperation((Float) val.literal, secondValue);
                                            value = op.eval();
                                        } case "/" -> {
                                            DivideOperation op = new DivideOperation((Float) val.literal, secondValue);
                                            value = op.eval();
                                        } case "%" -> {
                                            ModulusOperation op = new ModulusOperation((Float) val.literal, secondValue);
                                            value = op.eval();
                                        } case "^" -> {
                                            PowerOperation op = new PowerOperation((Float) val.literal, secondValue);
                                            value = op.eval();
                                        } case null, default -> {
                                            System.out.println("How did you even do this?");
                                            return -1;
                                        }
                                    }
                                } case null, default -> {
                                    System.out.println("How did you even end up here? It's really not supposed to be possible...");
                                }
                            }
                        }
                    } case KEYWORD -> {
                        System.out.println("Seriously? Oh my god... you can't- *sigh*");
                    } case IDENTIFIER -> {
                        Variable<?> firstVarFound = Parser.findTempVar(val.lexeme);
                        Float firstVal;
                        if (firstVarFound != null) {
                            type = firstVarFound.getType();
                        } else {
                            System.out.println("That didn't return anything, so this value ain't shit");
                            return -1;
                        }

                        if (type != VariableType.NUMBER) {
                            System.out.println("NO. STOP THAT. NUMBERS ONLY!");
                            return -1;
                        }

                        firstVal = (Float) firstVarFound.getValue();

                        Token next = filtered.get(5);
                        if (next.type != TokenType.OPERATOR) {
                            System.out.println("Do you even know how to code? AN OPERATOR BELONGS HERE!!!!");
                            return -1;
                        } else if (next.lexeme.equals("=") || next.lexeme.equals("<") || next.lexeme.equals(">") || next.lexeme.equals(";") || next.lexeme.equals("&") || next.lexeme.equals("|") || next.lexeme.equals(":") || next.lexeme.equals("$") || next.lexeme.equals("€")) { // '+', '-', '*', '/', '=', '<', '>', ';', '%', '&', '|', '^', ':', '$', '€'
                            System.out.println("Jeez. Make your comparators or assignments or whatnot somewhere else");
                            return -1;
                        } else if (filtered.get(6).type != TokenType.NUMBER && filtered.get(6).type != TokenType.IDENTIFIER) {
                            System.out.println("Seriously? Now? You were almost done with the arithmetic operation and you say '" + filtered.get(6).lexeme + "'");
                            return -1;
                        } else {
                            Token secondVal = filtered.get(6);
                            switch (secondVal.type) {
                                case NUMBER -> {
                                    switch (next.lexeme) {
                                        case "+" -> {
                                            IncrementOperation op = new IncrementOperation(firstVal, (Float) secondVal.literal);
                                            value = op.eval();
                                        } case "-" -> {
                                            DecrementOperation op = new DecrementOperation(firstVal, (Float) secondVal.literal);
                                            value = op.eval();
                                        } case "*" -> {
                                            MultiplyOperation op = new MultiplyOperation(firstVal, (Float) secondVal.literal);
                                            value = op.eval();
                                        } case "/" -> {
                                            DivideOperation op = new DivideOperation(firstVal, (Float) secondVal.literal);
                                            value = op.eval();
                                        } case "%" -> {
                                            ModulusOperation op = new ModulusOperation(firstVal, (Float) secondVal.literal);
                                            value = op.eval();
                                        } case "^" -> {
                                            PowerOperation op = new PowerOperation(firstVal, (Float) secondVal.literal);
                                            value = op.eval();
                                        } case null, default -> {
                                            System.out.println("How did you even do this?");
                                            return -1;
                                        }
                                    }
                                } case IDENTIFIER -> {
                                    Variable<?> varFound = Parser.findTempVar(secondVal.lexeme);
                                    if (varFound == null) {
                                        System.out.println("What is that identifier '" + secondVal.lexeme + "' supposed to be?");
                                        return -1;
                                    }
                                    Float secondValue = (Float) varFound.getValue();
                                    switch (next.lexeme) {
                                        case "+" -> {
                                            IncrementOperation op = new IncrementOperation(firstVal, secondValue);
                                            value = op.eval();
                                        } case "-" -> {
                                            DecrementOperation op = new DecrementOperation(firstVal, secondValue);
                                            value = op.eval();
                                        } case "*" -> {
                                            MultiplyOperation op = new MultiplyOperation(firstVal, secondValue);
                                            value = op.eval();
                                        } case "/" -> {
                                            DivideOperation op = new DivideOperation(firstVal, secondValue);
                                            value = op.eval();
                                        } case "%" -> {
                                            ModulusOperation op = new ModulusOperation(firstVal, secondValue);
                                            value = op.eval();
                                        } case "^" -> {
                                            PowerOperation op = new PowerOperation(firstVal, secondValue);
                                            value = op.eval();
                                        } case null, default -> {
                                            System.out.println("How did you even do this?");
                                            return -1;
                                        }
                                    }
                                } case null, default -> {
                                    System.out.println("How did you even end up here? It's really not supposed to be possible...");
                                }
                            }
                        }
                    }
                }
            } else {
                System.out.println("Incomplete statement... *sigh*");
                return -1;
            }
        }
        assert name != null;
        assert value != null;
        assert type != null;
        switch (type) {
            case NUMBER -> {
                VariableAssignment<Float> outputNode = new VariableAssignment<>(name, type, level, (Float) value);
                Variable<Float> tempVar = new Variable<>(level, type, name, (Float) value);
                Parser.tempVars.add(tempVar);
                output.add(outputNode);
                return i + untilBang.size();
            } case STRING -> {
                VariableAssignment<String> outputNode = new VariableAssignment<>(name, type, level, (String) value);
                Variable<String> tempVar = new Variable<>(level, type, name, (String) value);
                Parser.tempVars.add(tempVar);
                output.add(outputNode);
                return i + untilBang.size();
            } case BOOLEAN -> {
                VariableAssignment<Boolean> outputNode = new VariableAssignment<>(name, type, level, (Boolean) value);
                Variable<Boolean> tempVar = new Variable<>(level, type, name, (Boolean) value);
                Parser.tempVars.add(tempVar);
                output.add(outputNode);
                return i + untilBang.size();
            } case null, default -> {
                System.out.println("Something really went wrong here");
            }
        }
        return -1;
    }
}
