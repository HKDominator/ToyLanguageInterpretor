package model.expressions;

import model.adt.dictionary.IGenericDictionary;
import model.adt.exceptions.AppExceptions;
import model.adt.exceptions.DivisionByZero;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class ArithmeticExpression implements IExpression {
    private IExpression left;
    private IExpression right;
    int operator;

    public ArithmeticExpression(int operator, IExpression left, IExpression right) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public IValue evaluate(IGenericDictionary<String, IValue> table) throws AppExceptions {
        if( operator != '*' && operator != '+' && operator != '-' && operator != '/' ) {
            throw new AppExceptions("Arithmetic operator is not supported");
        }

        IValue rightValue, leftValue;
        leftValue = left.evaluate(table);
        if(leftValue.getType().equals(new IntType())) {
            rightValue = right.evaluate(table);
            if( rightValue.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) leftValue;
                IntValue i2 = (IntValue) rightValue;
                int leftActualValue = i1.getValue();
                int rightActualValue = i2.getValue();
                switch(operator) {
                    case '+':
                        return new IntValue(leftActualValue + rightActualValue);
                    case '-':
                        return new IntValue(leftActualValue - rightActualValue);
                    case '*':
                        return new IntValue(leftActualValue * rightActualValue);
                    case '/': {
                        if (rightActualValue == 0) {
                            throw new DivisionByZero("Cannot divide by zero");
                        } else return new IntValue(leftActualValue / rightActualValue);
                    }
                }
            }
            else {
                throw new AppExceptions("Second operand is not an integer");
            }
        }
        else {throw new AppExceptions("First operand is not an integer");}
        return leftValue;
    }

    @Override
    public IType typecheck(IGenericDictionary<String, IType> dict) throws AppExceptions {
        IType typeOfFirstExpression = left.typecheck(dict);
        IType typeOfSecondExpression = right.typecheck(dict);
        if( !typeOfFirstExpression.equals(new IntType()) ) {
            throw new AppExceptions("First operand is not an integer");
        }
        if( !typeOfSecondExpression.equals(new IntType()) ) {
            throw new AppExceptions("Second operand is not an integer");
        }
        return new  IntType();
    }

    @Override
    public IExpression deepcopy() {
        return new ArithmeticExpression(operator,left.deepcopy(), right.deepcopy());
    }

    @Override
    public String toString()
    {
        return left.toString() + (char) operator + right.toString();
    }

}
