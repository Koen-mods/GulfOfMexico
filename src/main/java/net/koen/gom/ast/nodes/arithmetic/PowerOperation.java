package net.koen.gom.ast.nodes.arithmetic;

public class PowerOperation implements ArithmeticOperation {
    float a;
    float b;

    public PowerOperation(float a, float b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public float eval() {
        return (float) Math.pow(a, b);
    }
}
