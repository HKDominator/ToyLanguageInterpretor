package model.types;

import model.values.BoolValue;
import model.values.IValue;

public class BoolType implements IType{

    public static final boolean DEFAULT_VALUE = false;

    @Override
    public IValue getDefaultValue() {
        return new BoolValue(DEFAULT_VALUE);
    }

    @Override
    public boolean equals(IType other) {
        return (other instanceof BoolType);
    }

    @Override
    public IType deepCopy() {
        return new BoolType();
    }

    public String toString()
    {
        return "bool";
    }

}
