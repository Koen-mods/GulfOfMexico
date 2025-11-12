package net.koen.gom.ast.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Function implements Node {
    List<Argument> params;
    CodeBlock code;

    public Function(CodeBlock code, Argument... parameters) {
        this.code = code;
        this.params = new ArrayList<>();
        this.params.addAll(Arrays.asList(parameters));
    }

    public List<Argument> getParameters() {
        return this.params;
    }

    public Object getValueOfParam(String name) {
        for (Argument arg : this.params) {
            if (arg.name.equals(name)) {
                return arg.value;
            }
        }
        return null;
    }

    @Override
    public void execute() {
        code.execute();
    }
}
