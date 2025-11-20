package model.expressions;

import model.adt.dictionary.IGenericDictionary;
import model.adt.exceptions.AppExceptions;
import model.adt.heap.IHeap;
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
    public IValue evaluate(IGenericDictionary<String, IValue> table, IHeap myHeap) throws AppExceptions {
        if( operator != '|' && operator != '&')
            throw new AppExceptions("Logic operator not supported");
        IValue leftValue, rightValue;
        leftValue = left.evaluate(table, myHeap);
        if( leftValue instanceof BoolType )
        {
            rightValue = right.evaluate(table, myHeap);
            if( rightValue instanceof BoolType )
            {
                BoolValue b1 = (BoolValue)rightValue;
                BoolValue b2 = (BoolValue)leftValue;
                boolean leftActualValue = b1.getValue();
                boolean rightActualValue = b2.getValue();
                return switch(operator) {
                    case '|'-> new BoolValue(leftActualValue | rightActualValue);
                    case '&' -> new BoolValue(leftActualValue & rightActualValue);
                    default ->  throw new AppExceptions("operator not supported");
                };
            }
            else {
                throw new AppExceptions("Second operand in not of type bool!");
            }
        }
        else {
            throw new AppExceptions("First operand is not of type bool!");
        }
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
