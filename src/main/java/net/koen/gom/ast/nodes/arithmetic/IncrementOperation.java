package net.koen.gom.ast.nodes.arithmetic;

public class IncrementOperation implements ArithmeticOperation {
    float a;
    float b;

    public IncrementOperation(float a, float b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public float eval() {
        return a + b;
    }
}
