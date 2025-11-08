package net.koen.gom.ast.nodes.booleanexpressions;

public final class LiteralExpression implements BooleanExpression {
    private final boolean value;

    public LiteralExpression(boolean value) {
        this.value = value;
    }

    @Override
    public boolean evaluate() {
        return value;
    }
}
