package net.koen.gom.execution.functions;

import net.koen.gom.execution.ParameterBinding;
import net.koen.gom.execution.variables.LocalVariableSystem;
import net.koen.gom.lexing.Token;

import java.util.List;

public class Function {
    List<Token> code;
    String name;
    List<String> parameters;
    LocalVariableSystem localVariableSystem = new LocalVariableSystem();

    public Function(List<Token> code, String name, List<String> parameters) {
        this.code = code;
        this.name = name;
        this.parameters = parameters;
    }

    public void call(ParameterBinding... args) {
        if (args.length != parameters.size()) {
            System.out.println("You can't call " + name + " with this number of parameters... (Line " + code.get(1).line + ")\nIt should be " + parameters.size() + " but you've given " + args.length + "!");
        }
    }
}
