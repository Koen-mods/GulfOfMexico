package net.koen.gom.execution.variables;

public enum VariableLevel {
    CONSTCONST("Constant constant"),
    CONSTVAR("Constant variable"),
    VARCONST("Variable constant"),
    VARVAR("Variable variable");

    String name;

    VariableLevel(String name) {
        this.name = name;
    }
}
