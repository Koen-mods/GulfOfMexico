package net.koen.gom.execution.functions;

import net.koen.gom.execution.variables.Variable;
import net.koen.gom.execution.variables.VariableLevel;
import net.koen.gom.execution.variables.VariableType;
import net.koen.gom.lexing.Token;

import java.util.ArrayList;
import java.util.List;

public class FunctionStorage {
    public static List<Function> Functions = new ArrayList<>();

    public static Function findVar(String name) {
        for (Function func : Functions) {
            if (func.name.equals(name)) {
                return func;
            }
        }
        return null;
    }

    public static boolean createFunction(String name, List<Token> code, List<String> parameters) {
        for (Function func : Functions) {
            if (func.name.equals(name)) {
                System.out.println("A function with that name (" + name + ") already existed");
                return false;
            }
        }
        Function func = new Function(code, name, parameters);
        Functions.add(func);
        return true;
    }
}
