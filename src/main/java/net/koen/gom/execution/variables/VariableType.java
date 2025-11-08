package net.koen.gom.execution.variables;

public enum VariableType {
    STRING("String"),
    NUMBER("Number"),
    BOOLEAN("bool");

    String lexeme;

    VariableType(String lexeme) {
        this.lexeme = lexeme;
    }
}
