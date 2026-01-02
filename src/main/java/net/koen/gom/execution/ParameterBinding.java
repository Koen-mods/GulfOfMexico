package net.koen.gom.execution;

import net.koen.gom.execution.variables.VariableType;

public class ParameterBinding<T> {
    T value;
    String name;
    VariableType type;

    public ParameterBinding(T value, String name, VariableType type) {
        this.value = value;
        this.name = name;
        this.type = type;
    }

    public T getValue() {
        return this.value;
    }
}
