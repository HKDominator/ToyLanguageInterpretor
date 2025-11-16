package model.expressions;

import model.adt.dictionary.IGenericDictionary;
import model.adt.exceptions.AppExceptions;
import model.types.IType;
import model.values.IValue;

public class VariableExpression implements IExpression {
    private String idOfVariable;

    public VariableExpression(String idOfVariable) {
        this.idOfVariable = idOfVariable;
    }

    @Override
    public IValue evaluate(IGenericDictionary<String, IValue> table) throws AppExceptions {
        return table.lookup(idOfVariable);
    }

    @Override
    public IType typecheck(IGenericDictionary<String, IType> dict) throws AppExceptions {
        return dict.lookup(idOfVariable);
    }

    @Override
    public IExpression deepcopy() {
        return new  VariableExpression(this.idOfVariable);
    }

    @Override
    public String toString() {
        return idOfVariable;
    }
}
