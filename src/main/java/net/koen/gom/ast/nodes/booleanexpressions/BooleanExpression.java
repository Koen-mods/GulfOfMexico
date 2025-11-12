package net.koen.gom.ast.nodes.booleanexpressions;

import net.koen.gom.ast.nodes.Node;

public sealed interface BooleanExpression extends Node permits LiteralExpression, ComparatorExpression, NestedExpression {
    boolean evaluate();
    default void execute() {
        // Nothing should happen
    }
}
