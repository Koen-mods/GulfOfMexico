package net.koen.gom.ast.nodes.arithmetic;

public class MultiplyOperation implements ArithmeticOperation {
    float left;
    float right;

    public MultiplyOperation(float left, float right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public float eval() {
        return left * right;
    }
}

