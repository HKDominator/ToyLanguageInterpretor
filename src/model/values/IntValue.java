package model.values;

import model.adt.exceptions.AppExceptions;
import model.types.IType;
import model.types.IntType;

public class IntValue implements IValue {
    private int value;
    /// add in general enum, implement the IGenericTable
    public IntValue()
    {
        this.value = 0;
    }

    public IntValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public boolean equals(IValue other) {
        if(!(other instanceof IntValue) ){
            return false;
        }
        return value == ((IntValue)other).value;
    }

    @Override
    public IValue deepCopy() {
        return new IntValue(value);
    }

    @Override
    public String toString()
    {
        return String.valueOf(value);
    }

}
