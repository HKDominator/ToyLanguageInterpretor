package model.expressions;

import model.adt.dictionary.IGenericDictionary;
import model.adt.exceptions.AppExceptions;
import model.adt.heap.IHeap;
import model.types.IType;
import model.types.ReferenceType;
import model.values.IValue;
import model.values.ReferenceValue;

import java.beans.Expression;

public class HeapReadingExpression implements IExpression {
    private IExpression expression;

    public HeapReadingExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IValue evaluate(IGenericDictionary<String, IValue> table, IHeap heap) throws AppExceptions {
        if( !(expression.evaluate(table, heap) instanceof ReferenceValue) ) {
            throw new AppExceptions("the given expression is not of reference value");
        }

        int addressOfReference = ((ReferenceValue) expression.evaluate(table, heap)).getaddress();
        if( heap.get(addressOfReference) == null ) {
            throw new AppExceptions("invalid address of reference");
        }
        return heap.get(addressOfReference);
    }

    @Override
    public IType typecheck(IGenericDictionary<String, IType> dict) throws AppExceptions {
        IType typeOfExpression = expression.typecheck(dict);

        if( typeOfExpression instanceof ReferenceType ) {
            return ((ReferenceType)typeOfExpression).getTypeOfPointedValue();
        }
        throw new AppExceptions("expression is not of type reference");
    }

    @Override
    public IExpression deepcopy() {
        return new HeapReadingExpression(expression.deepcopy());
    }

    @Override
    public String toString(){
        return "readHeap(" +  expression.toString() + ")";
    }
}
