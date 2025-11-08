package net.koen.gom.lexing;

public enum Keyword {
    // All keywords
    IF("if"),
    WHEN("when"),
    TRUE("true"),
    FALSE("false"),
    DELETE("delete"),
    CLASS("class"),
    CLASSNAME("className"),
    VAR("var"),
    CONST("const"),
    STRING("String"),
    INTEGER("int"),
    FUNCTION("function");

    // Keyword attributes
    public final String value;
    public boolean deleted;

    Keyword(String value) {
        this.value = value;
        this.deleted = false;
    }

    // Method to set the state of a keyword deleted or not. Will be set to true for every keyword if the no-keywords flag is provided
    void setDeleted(boolean value)
    {
        this.deleted = value;
    }
}
