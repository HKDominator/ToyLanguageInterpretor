package model.expressions;

import model.adt.dictionary.IGenericDictionary;
import model.adt.exceptions.AppExceptions;
import model.types.IType;
import model.values.IValue;

import java.beans.Expression;

public class ValueExpression implements IExpression {
    private IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }

    @Override
    public IValue evaluate(IGenericDictionary<String, IValue> table) throws AppExceptions {
        return value;
    }

    @Override
    public IType typecheck(IGenericDictionary<String, IType> dict) throws AppExceptions {
        return value.getType();
    }

    @Override
    public IExpression deepcopy() {
        return new ValueExpression(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
