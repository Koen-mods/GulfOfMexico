package net.koen.gom;

import net.koen.gom.execution.variables.Variable;
import net.koen.gom.execution.variables.VariableLevel;
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
        String code = "var var word: String = 3 + 3?\nprint(\"€{word}\")!";
        List<Token> output = Lexer.Lex(code);
        for (Token tok : output) {
            System.out.println(tok.toString());
        }
    }
}