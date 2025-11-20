package model.types;

import model.values.IValue;
import model.values.StringValue;

public class StringType implements IType{
    public static final StringValue DEFAULT_VALUE = new StringValue("");
    @Override
    public IValue getDefaultValue() {
        return DEFAULT_VALUE;
    }

    @Override
    public boolean equals(IType other) {
        return ( other instanceof StringType );
    }

    @Override
    public IType deepCopy() {
        return new StringType();
    }

    @Override
    public String toString(){
        return "string";
    }
}
