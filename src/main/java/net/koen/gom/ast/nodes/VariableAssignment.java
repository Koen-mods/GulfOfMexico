package net.koen.gom.ast.nodes;

import net.koen.gom.execution.variables.VariableLevel;
import net.koen.gom.execution.variables.VariableSystem;
import net.koen.gom.execution.variables.VariableType;

public class VariableAssignment<T> implements Node {
    public String name;
    VariableType type;
    VariableLevel level;
    T value;
    boolean resolvesToFunction = false;

    public VariableAssignment(String name, VariableType type, VariableLevel level, T value) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.value = value;
    }

    public void MarkRemoved() {
        this.resolvesToFunction = true;
    }

    public boolean isResolvedToFunction() {
        return resolvesToFunction;
    }

    @Override
    public void execute() {
        if (resolvesToFunction) return;
        VariableSystem.createVariable(name, type, level, value);
    }
}
