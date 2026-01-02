package net.koen.gom.ast.parsing.helpers;

import net.koen.gom.ast.nodes.Node;
import net.koen.gom.ast.parsing.Parser;
import net.koen.gom.execution.ParameterBinding;
import net.koen.gom.execution.functions.Function;
import net.koen.gom.execution.variables.Variable;
import net.koen.gom.execution.variables.VariableSystem;
import net.koen.gom.execution.variables.VariableType;
import net.koen.gom.lexing.Lexer;
import net.koen.gom.lexing.Token;

import java.util.List;

public class General {
    public static class testing {
        public static class variables {
            public static void simple() {
                String code = "const const var1 = 2^3!\nvar const var2 = var1!\nvar2 = 7!\nvar var var3 = 2!\nvar const var4 = 2-var3!\nvar var var5 = 1!\nvar var var6 = var5 / var1!\nvar var var7 = 100 % 15!";
                List<Token> lexed = Lexer.Lex(code);
                for (Token tok : lexed) {
                    System.out.println(tok.toString());
                }
                List<Node> AST = Parser.parse(lexed);
                for (Node node : AST) {
                    node.execute();
                }
                for (Variable var : VariableSystem.Variables) {
                    System.out.println("Name: " + var.getName() + "\n" + "Value: " + var.getValue());
                }
            }
        }

        public static class functions {
            public static void simple() {
                String code = "var var var1 = 1!\nfunction func(a, b) { var var var2 = 2 }!";
                List<Token> lexed = Lexer.Lex(code);
                for (Token tok : lexed) {
                    System.out.println(tok.toString());
                }
                List<Node> AST = Parser.parse(lexed);
                for (Node node : AST) {
                    node.execute();
                }
                for (Variable var : VariableSystem.Variables) {
                    System.out.println("Name: " + var.getName() + "\n" + "Value: " + var.getValue());
                }
            }

            public static void call() {
                String code = "var var var1 = 1!";
                List<Token> lexed = Lexer.Lex(code);
                Function func = new Function(lexed, "testingFunc", List.of("name"));
                ParameterBinding<String> name = new ParameterBinding<String>("Koen", "name", VariableType.STRING);
                func.call(name, new ParameterBinding<Float>(4f, "times", VariableType.NUMBER));
            }
        }
    }
}
