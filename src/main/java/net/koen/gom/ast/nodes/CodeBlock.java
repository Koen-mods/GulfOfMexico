package net.koen.gom.ast.nodes;

import net.koen.gom.lexing.Token;

import java.util.List;

public class CodeBlock {
    // List of all tokens in block
    List<Token> code;

    // Constructor method
    public CodeBlock(List<Token> code) {
        this.code = code;
    }

    private void parse() {
        // Parse this.code to AST
    }

    public void execute() {
        // Execute the code
        this.parse();
    }
}
