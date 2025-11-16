package model.values;

import model.adt.exceptions.AppExceptions;
import model.types.BoolType;
import model.types.IType;

public class BoolValue implements IValue{

    private boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }

    public boolean getValue()
    {
        return value;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public boolean equals(IValue other) {
        if( !(other instanceof BoolValue) ) return false;
        return value == ((BoolValue)other).getValue();
    }

    @Override
    public IValue deepCopy() {
        return new BoolValue(value);
    }

    @Override
    public String toString(){
        return  String.valueOf(value);
    }

    public boolean equals(Object other) {
        if( !(other instanceof BoolValue) ) return false;
        return value == ((BoolValue)other).getValue();
    }
}
