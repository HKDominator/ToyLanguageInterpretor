package model.values;

import model.types.IType;
import model.types.StringType;

import java.util.Objects;

public class StringValue implements IValue {

    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    public StringValue(){
        value = "";
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(IValue other) {
        if( !(other instanceof StringValue))
            return false;
        return Objects.equals(value, ((StringValue)other).getValue());
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
