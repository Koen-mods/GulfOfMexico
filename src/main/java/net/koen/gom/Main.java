package net.koen.gom;

import net.koen.gom.ast.nodes.Node;
import net.koen.gom.ast.parsing.Parser;
import net.koen.gom.execution.variables.Variable;
import net.koen.gom.execution.variables.VariableSystem;
import net.koen.gom.lexing.Keyword;
import net.koen.gom.lexing.Lexer;
import net.koen.gom.lexing.Token;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        for (String arg : args)
        {
            switch (arg)
            {
                case "-nk", "--no-keywords":
                    for (Keyword k : Keyword.values())
                    {
                        k.deleted = true;
                    }
            }
        }
        String code = "const const var1 = 14!\nvar const var2 = var1!";
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