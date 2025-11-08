package net.koen.gom.execution.variables;

public class Variable<T> {
    VariableType type;
    VariableLevel level;
    String name;
    T value;

    public Variable(VariableLevel level, VariableType type, String name, T value) {
        this.level = level;
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }

    public VariableType getType() {
        return type;
    }

    public VariableLevel getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
