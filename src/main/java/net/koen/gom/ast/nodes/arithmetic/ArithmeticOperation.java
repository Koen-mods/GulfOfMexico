package net.koen.gom.ast.nodes.arithmetic;

import net.koen.gom.ast.nodes.Node;

public interface ArithmeticOperation extends Node {
    float eval();
    default void execute() {
        // Nothing should happen
    }
}
