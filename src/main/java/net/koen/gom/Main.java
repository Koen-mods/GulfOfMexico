package net.koen.gom;

import net.koen.gom.ast.nodes.booleanexpressions.ComparatorExpression;
import net.koen.gom.ast.nodes.booleanexpressions.LiteralExpression;
import net.koen.gom.ast.nodes.controlflow.IfStatement;
import net.koen.gom.ast.parsing.helpers.General;
import net.koen.gom.lexing.Keyword;
import net.koen.gom.lexing.Token;
import net.koen.gom.lexing.TokenType;

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
                case "-v", "--version":
                    System.out.println("GulfOfMexico 0.0.1");
                    return;
            }
        }

        ComparatorExpression expr = new ComparatorExpression<Integer>(1, ";=", 2);

        IfStatement stmt = new IfStatement(expr, List.of(new Token(TokenType.BANG, "!", null, 1, 1)));

        stmt.execute();
    }
}