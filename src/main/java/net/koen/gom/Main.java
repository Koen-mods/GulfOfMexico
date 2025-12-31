package net.koen.gom;

import net.koen.gom.ast.parsing.helpers.General;
import net.koen.gom.lexing.Keyword;

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
                    System.out.println("GulfOfMexico interpreter 0.0.1");
                    return;
            }
        }
        General.testing.variables.simple();
    }
}