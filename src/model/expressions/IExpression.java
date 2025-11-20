package model.expressions;

import model.adt.dictionary.IGenericDictionary;
import model.adt.exceptions.AppExceptions;
import model.adt.heap.IHeap;
import model.values.IValue;
import model.types.IType;

public interface IExpression {

    IValue evaluate(IGenericDictionary<String, IValue> table, IHeap heap) throws AppExceptions;
    String toString();
    IType typecheck(IGenericDictionary<String, IType> dict) throws AppExceptions;
    IExpression deepcopy();
}
