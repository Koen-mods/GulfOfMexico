package net.koen.gom.ast.nodes.booleanexpressions;

public sealed interface BooleanExpression permits LiteralExpression, ComparatorExpression, NestedExpression {
    boolean evaluate();
}
