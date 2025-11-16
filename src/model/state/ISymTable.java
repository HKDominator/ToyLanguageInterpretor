package model.state;

import model.adt.exceptions.AppExceptions;
import model.values.IValue;
import model.types.IType;

import java.util.Map;

public interface ISymTable {

    void declareValue(String name, IType type) throws AppExceptions;

    IValue getValue(String name) throws AppExceptions;

    void setValue(String name, IValue value) throws AppExceptions;

    String toString();

    ISymTable copy() throws AppExceptions;

    Map<String, IValue> getMap();
}
