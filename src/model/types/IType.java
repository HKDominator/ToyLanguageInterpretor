package model.types;

import model.adt.exceptions.AppExceptions;
import model.values.IValue;

public interface IType {
    IValue getDefaultValue();
    boolean equals(IType other);
    IType deepCopy();
}
