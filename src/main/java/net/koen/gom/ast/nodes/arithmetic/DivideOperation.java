package net.koen.gom.ast.nodes.arithmetic;

public class DivideOperation implements ArithmeticOperation {
    float a;
    float b;

    public DivideOperation(float a, float b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public float eval() {
        return a / b;
    }
}
