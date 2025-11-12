package net.koen.gom.ast.nodes;

import net.koen.gom.execution.variables.VariableSystem;

public class VariableEdit<T> implements Node {
    String name;
    T newValue;

    public VariableEdit(String name, T newValue) {
        this.name = name;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        VariableSystem.editVariable(name, newValue);
    }
}
