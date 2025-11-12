package net.koen.gom.ast.nodes;

import net.koen.gom.execution.variables.VariableLevel;
import net.koen.gom.execution.variables.VariableSystem;
import net.koen.gom.execution.variables.VariableType;

public class VariableAssignment<T> implements Node {
    String name;
    VariableType type;
    VariableLevel level;
    T value;

    public VariableAssignment(String name, VariableType type, VariableLevel level, T value) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.value = value;
    }

    @Override
    public void execute() {
        VariableSystem.createVariable(name, type, level, value);
    }
}
