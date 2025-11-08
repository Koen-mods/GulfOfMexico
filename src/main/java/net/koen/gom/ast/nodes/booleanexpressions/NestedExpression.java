package net.koen.gom.ast.nodes.booleanexpressions;

public final class NestedExpression implements BooleanExpression {
    private final BooleanExpression left;
    private final String operator; // "&&" or "||"
    private final BooleanExpression right;

    public NestedExpression(BooleanExpression left, String operator, BooleanExpression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public boolean evaluate() {
        return switch (operator) {
            case "&&" -> left.evaluate() && right.evaluate();
            case "||" -> left.evaluate() || right.evaluate();
            default -> throw new IllegalArgumentException("Unknown logical operator: " + operator);
        };
    }
}
