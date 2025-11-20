package model.values;

import model.types.IType;
import model.types.ReferenceType;

public class ReferenceValue implements IValue{
    private int address;
    private IType locationType;

    public ReferenceValue(int address, IType locationType){
        this.address = address;
        this.locationType = locationType;
    }

    public int getaddress(){
        return address;
    }

    public IType getLocationType(){
        return locationType;
    }

    @Override
    public IType getType() {
        return new ReferenceType(locationType);
    }

    @Override
    public boolean equals(IValue other) {
        if(other instanceof ReferenceValue){
            return address == ((ReferenceValue)other).address &&
                    locationType == ((ReferenceValue)other).locationType;
        }
        return false;
    }

    @Override
    public IValue deepCopy() {
        return new ReferenceValue(address, locationType.deepCopy());
    }

    @Override
    public String toString() {
        return "(" + address + "," + locationType + ")";
    }
}
