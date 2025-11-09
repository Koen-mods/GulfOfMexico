package net.koen.gom.execution.variables;

import java.util.ArrayList;
import java.util.List;

public class VariableSystem {
    public static List<Variable<?>> Variables = new ArrayList<>();

    public static Variable findVar(String name) {
        for (Variable var : Variables) {
            if (var.getName().equals(name)) {
                return var;
            }
        }
        return null;
    }

    public static boolean createVariable(String name, VariableType type, VariableLevel level, Object value) {
        for (Variable var : Variables) {
            if (var.getName().equals(name)) {
                if (var.getType() == type && var.getLevel() == level) {
                    var.setValue(value);
                    return true;
                } else {
                    System.out.println("A variable with that name already existed, that's fine, but keep it the same type and level!");
                }
            }
        }
        switch (type) {
            case NUMBER -> {
                if (!(value instanceof Float)) {
                    System.out.println("Variable with name " + name + " is not being created because something went wrong real bad in here");
                    return false;
                }
                Variable<Float> var = new Variable<>(level, type, name, (Float) value);
                Variables.add(var);
                return true;
            }
            case STRING -> {
                if (!(value instanceof String)) {
                    System.out.println("Variable with name " + name + " is not being created because something went wrong real bad in here");
                    return false;
                }
                Variable<String> var = new Variable<>(level, type, name, (String) value);
                Variables.add(var);
                return true;
            }
            case BOOLEAN -> {
                if (!(value instanceof Boolean)) {
                    System.out.println("Variable with name " + name + " is not being created because something went wrong real bad in here");
                    return false;
                }
                Variable<Boolean> var = new Variable<>(level, type, name, (Boolean) value);
                Variables.add(var);
                return true;
            }
        }
        return false;
    }

    public static boolean editVariable(String name, Object newValue) {
        Variable var = findVar(name);
        if (var == null) {
            System.out.println("No variable with that name present!");
            return false;
        } else {
            VariableType type = var.getType();
            VariableLevel level = var.getLevel();
            if (level != VariableLevel.VARCONST && level != VariableLevel.VARVAR) {
                System.out.println("You can't edit that bro... it's a " + level.name);
                return false;
            }
            switch (type) {
                case NUMBER -> {
                    if (!(newValue instanceof Float)) {
                        System.out.println("Keep it the same type at least, okay?");
                        return false;
                    } else {
                        var.setValue(newValue);
                        return true;
                    }
                } case STRING -> {
                    if (!(newValue instanceof String)) {
                        System.out.println("Keep it the same type at least, okay?");
                        return false;
                    } else {
                        var.setValue(newValue);
                        return true;
                    }
                } case BOOLEAN -> {
                    if (!(newValue instanceof Boolean)) {
                        System.out.println("Keep it the same type at least, okay?");
                        return false;
                    } else {
                        var.setValue(newValue);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
