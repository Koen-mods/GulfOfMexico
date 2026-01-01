package net.koen.gom.ast.parsing.functions;

import net.koen.gom.ast.nodes.FunctionDeclaration;
import net.koen.gom.ast.nodes.Node;
import net.koen.gom.ast.nodes.VariableAssignment;
import net.koen.gom.ast.parsing.Parser;
import net.koen.gom.ast.parsing.helpers.inputFiltering;
import net.koen.gom.execution.variables.Variable;
import net.koen.gom.lexing.Token;
import net.koen.gom.lexing.TokenType;

import java.util.ArrayList;
import java.util.List;

public class FunctionDeclParser {
    public static int Parse(List<Token> input, int start, List<Node> output) {
        Node outputNode = null;
        int LBracePos;
        boolean FirstBracePresent = false;
        Token tok = input.get(start);
        int j = start;
        while (tok.type != TokenType.NEWLINE && tok.type != TokenType.BANG && tok.type != TokenType.EOF) {
            tok = input.get(j);
            if (tok.type == TokenType.LBRACE) {
                FirstBracePresent = true;
                break;
            }
            j++;
        }

        if (!FirstBracePresent) {
            return -1;
        }

        List<Token> declaration = inputFiltering.consumeUntilLBrace(input, start);
        LBracePos = start + declaration.size();

        List<Token> filtered = inputFiltering.filterSpaces(declaration);
        declaration = filtered;
        if (filtered.size() < 4) {
            System.out.println("You need more to declare that function o' yours, line " + filtered.get(start).line);
            return -1;
        }

        int currIdx = 0;
        Token curr = declaration.get(currIdx);

        if (curr.lexeme != "function") {
            System.out.println("This really is not supposed to be possible, so if you are seeing this it is likely an interpreter error, and not a code error.");
            return -1;
        }

        currIdx++;
        curr = declaration.get(currIdx);

        if (curr.type != TokenType.IDENTIFIER) {
            System.out.println("You know you can't do that! Line " + curr.line);
        }

        Variable<?> PossVar = Parser.findTempVar(curr.lexeme);
        String name = curr.lexeme;

        if (PossVar != null) {
            Parser.tempVars.remove(PossVar);
            for (Node n : output) {
                if (n instanceof VariableAssignment<?>) {
                    if (((VariableAssignment<?>) n).name.equals(PossVar.getName())) {
                        ((VariableAssignment<?>) n).MarkRemoved();
                    }
                }
            }
        }

        currIdx++;
        curr = declaration.get(currIdx);

        if (curr.type != TokenType.LPAREN) {
            System.out.println("You're missing a '(' over here you know, line " + curr.line);
            return -1;
        }

        currIdx = declaration.size() - 1;
        curr = declaration.get(currIdx);

        if (curr.type != TokenType.RPAREN) {
            System.out.println("You're missing a ')' over here you know, line " + curr.line);
            return -1;
        }

        currIdx = 3;
        TokenType expectedType = TokenType.IDENTIFIER;
        List<String> params = new ArrayList<>();
        while (declaration.get(currIdx).type != TokenType.RPAREN) {
            curr = declaration.get(currIdx);
            switch (curr.type) {
                case COMMA -> {
                    if (expectedType == TokenType.COMMA) {
                        expectedType = TokenType.IDENTIFIER;
                    } else {
                        System.out.println("No, you can't just do this! It should have been a comma! Line " + curr.line);
                        return -1;
                    }
                } case IDENTIFIER -> {
                    if (expectedType == TokenType.IDENTIFIER) {
                        params.add(curr.lexeme);
                        expectedType = TokenType.COMMA;
                    } else {
                        System.out.println("No, you can't just do this! It should have been an identifier! Line " + curr.line);
                        return -1;
                    }
                } case null, default -> {
                    System.out.println("Why did you even think about putting this here on line " + curr.line);
                }
            }
            currIdx++;
        }

        int endPos = input.size();
        int depth = 1;
        for (int i = LBracePos + 1; i < input.size(); i++) {
            Token Ctok = input.get(i);

            if (Ctok.type == TokenType.LBRACE) depth++;
            else if (Ctok.type == TokenType.RBRACE) depth--;

            if (depth == 0) {
                endPos = i;
                break;
            }
        }

        if (depth != 0) {
            System.out.println("Oh boy, you haven't finished your code block properly");
            return -1;
        }

        List<Token> codeBlock = input.subList(LBracePos, endPos);

        outputNode = new FunctionDeclaration(name, codeBlock, params);

        output.add(outputNode);


        return endPos;
    }
}
