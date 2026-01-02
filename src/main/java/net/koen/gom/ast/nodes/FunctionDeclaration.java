package net.koen.gom.ast.nodes;

import net.koen.gom.lexing.Token;

import java.util.List;

public class FunctionDeclaration implements Node {
    List<String> parameters;
    String name;
    List<Token> code;
    // Function func;

    public FunctionDeclaration(String name, List<Token> code, List<String> parameters) {
        this.name = name;
        this.code = code;
        this.parameters = parameters;
    }

    @Override
    public void execute() {
        // debug lines
        System.out.println("PARSED FUNCTION " + this.name);
        System.out.println("Function body:");
        for (Token tok : this.code) {
            System.out.println(tok.toString());
        }


    }
}
