package net.koen.gom.ast.parsing;

import net.koen.gom.ast.nodes.Node;
import net.koen.gom.ast.nodes.VariableAssignment;
import net.koen.gom.ast.parsing.variables.AssignmentParser;
import net.koen.gom.ast.parsing.variables.EditParser;
import net.koen.gom.execution.variables.Variable;
import net.koen.gom.execution.variables.VariableLevel;
import net.koen.gom.execution.variables.VariableSystem;
import net.koen.gom.execution.variables.VariableType;
import net.koen.gom.lexing.Token;
import net.koen.gom.lexing.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static List<Variable<?>> tempVars = new ArrayList<>();

    public static Variable<?> findTempVar(String name) {
        for (Variable var : tempVars) {
            if (var.getName().equals(name)) {
                return var;
            }
        }
        return null;
    }

    public static List<Node> parse(List<Token> input) {
        List<Node> output = new ArrayList<>();
        Token token;
        for (int i = 0; i < input.size(); i++) {
            token = input.get(i);
            String word = token.lexeme;
            TokenType type = token.type;
            switch (type) {
                case IDENTIFIER -> {
                    int success = EditParser.Parse(input, i, word, output);
                    if (success == -1) {
                        return null;
                    } else {
                        i = success;
                    }
                } case KEYWORD -> {
                    switch (word) {
                        case "const", "var" -> {
                            int success = AssignmentParser.parse(input, i, word, output);
                            if (success == -1) {
                                return null;
                            } else {
                                i = success;
                            }
                        }
                    }
                }
            }
        }
        return output;
    }
}
