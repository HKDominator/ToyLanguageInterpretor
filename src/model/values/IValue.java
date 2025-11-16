package model.values;

import model.adt.exceptions.AppExceptions;
import model.types.IType;

public interface IValue {
    String toString();

    IType getType();

    boolean equals(IValue other);

    IValue deepCopy();
}
