package model.types;

import model.values.IValue;
import model.values.IntValue;

public class IntType implements IType {
    private static final int DEFAULT_VALUE = 0;

    @Override
    public IValue getDefaultValue() {
        return new IntValue(DEFAULT_VALUE);
    }

    @Override
    public boolean equals(IType other) {
        return (other instanceof IntType);
    }

    @Override
    public IType deepCopy() {
        return new IntType();
    }

    @Override
    public String toString() {
        return "int";
    }
}
