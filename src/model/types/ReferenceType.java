package model.types;

import model.values.IValue;
import model.values.ReferenceValue;

public class ReferenceType implements IType{

    private IType typeOfPointedValue;

    public ReferenceType(IType typeOfPointedValue){
        this.typeOfPointedValue = typeOfPointedValue;
    }

    public IType getTypeOfPointedValue(){
        return typeOfPointedValue;
    }
    @Override
    public IValue getDefaultValue() {
        return new ReferenceValue(0, typeOfPointedValue);
    }

    @Override
    public boolean equals(IType other) {
        if( other instanceof ReferenceType){
            return typeOfPointedValue.equals(((ReferenceType) other).getTypeOfPointedValue());
        }
        else{
            return false;
        }
    }

    @Override
    public IType deepCopy() {
        return new ReferenceType(typeOfPointedValue.deepCopy());
    }

    @Override
    public String toString() {
        return "Ref " +  typeOfPointedValue.toString();
    }
}
