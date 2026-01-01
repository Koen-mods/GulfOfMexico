package net.koen.gom.ast.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Function implements Node {
    List<String> params;
    CodeBlock code;

    public Function(CodeBlock code, String... parameters) {
        this.code = code;
        this.params = new ArrayList<>();
        this.params.addAll(Arrays.asList(parameters));
    }

    public List<String> getParameters() {
        return this.params;
    }

    @Override
    public void execute() {
        code.execute();
    }
}
