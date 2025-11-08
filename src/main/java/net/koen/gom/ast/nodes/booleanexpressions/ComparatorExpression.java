package net.koen.gom.ast.nodes.booleanexpressions;

public final class ComparatorExpression<T extends Comparable<T>> implements BooleanExpression {
    private final T left;
    private final String operator;
    private final T right;

    public ComparatorExpression(T left, String operator, T right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public boolean evaluate() {
        int cmp = left.compareTo(right);
        return switch (operator) {
            case ">" -> cmp > 0;
            case "<" -> cmp < 0;
            case "==" -> cmp == 0;
            case ";=" -> cmp != 0;
            case ">=" -> cmp >= 0;
            case "<=" -> cmp <= 0;
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }
}
