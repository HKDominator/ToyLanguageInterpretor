package model.expressions;

import model.adt.dictionary.IGenericDictionary;
import model.adt.exceptions.AppExceptions;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class LogicExpression implements IExpression {
    IExpression left;
    IExpression right;
    int operator;

    public LogicExpression(IExpression left, IExpression right, int operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public IValue evaluate(IGenericDictionary<String, IValue> table) throws AppExceptions {
        if( operator != '|' && operator != '&')
            throw new AppExceptions("Logic operator not supported");
        IValue leftValue, rightValue;
        leftValue = left.evaluate(table);
        if( leftValue instanceof BoolType )
        {
            rightValue = right.evaluate(table);
            if( rightValue instanceof BoolType )
            {
                BoolValue b1 = (BoolValue)rightValue;
                BoolValue b2 = (BoolValue)leftValue;
                boolean leftActualValue = b1.getValue();
                boolean rightActualValue = b2.getValue();
                switch(operator) {
                    case '|': return new BoolValue(leftActualValue | rightActualValue);
                    case '&': return new BoolValue(leftActualValue & rightActualValue);
                }
            }
            else {
                throw new AppExceptions("Second operand in not of type bool!");
            }
        }
        else{
            throw new  AppExceptions("First operand is not of type bool!");
        }
        return null;
    }

    @Override
    public IType typecheck(IGenericDictionary<String, IType> dict) throws AppExceptions {
        IType leftType = left.typecheck(dict);
        IType rightType = right.typecheck(dict);
        if( !leftType.equals(new BoolType()) )
            throw new AppExceptions("The first argument is not a boolean type");
        if( !rightType.equals(new BoolType()) )
            throw new AppExceptions("The second argument is not a boolean type");
        return new BoolType();
    }

    @Override
    public IExpression deepcopy() {
        return new LogicExpression(left.deepcopy(), right.deepcopy(), operator);
    }

    @Override
    public String toString()
    {
        return "LogicExpression { " +
                "first_expression = " + left.toString() +
                "second_expression = " + right.toString() +
                ",operator= " + operator + "}";
    }

}
