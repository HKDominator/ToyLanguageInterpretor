package model.types;

import model.values.IValue;
import model.values.StringValue;

public class StringType implements IType{
    public static final String DEFAULT_VALUE = "";
    @Override
    public IValue getDefaultValue() {
        return new StringValue(DEFAULT_VALUE);
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
