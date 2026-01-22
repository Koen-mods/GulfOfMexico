package net.koen.gom.ast.nodes.controlflow;

import net.koen.gom.ast.nodes.Node;
import net.koen.gom.ast.nodes.booleanexpressions.BooleanExpression;
import net.koen.gom.lexing.Token;

import java.util.List;

import static net.koen.gom.util.Debug.printDB;

public class IfStatement implements Node {
    public BooleanExpression expression;
    public List<Token> tokens; // Temporary feature notice: will be replaced by AST

    public IfStatement(BooleanExpression expression, List<Token> tokens) {
        this.expression = expression;
        this.tokens = tokens;
    }

    public boolean resolve() {
        return expression.evaluate();
    }

    @Override
    public void execute() {
        printDB("Resolved to " + this.resolve());
    }
}
