package net.koen.gom.ast.nodes.arithmetic;

public class DecrementOperation implements ArithmeticOperation {
    float a;
    float b;

    public DecrementOperation(float a, float b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public float eval() {
        return a + b;
    }
}
