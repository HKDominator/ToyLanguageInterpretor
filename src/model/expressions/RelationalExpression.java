package model.expressions;

import model.adt.dictionary.IGenericDictionary;
import model.adt.exceptions.AppExceptions;
import model.adt.exceptions.TypeMismatch;
import model.adt.heap.IHeap;
import model.types.BoolType;
import model.types.IType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;

public class RelationalExpression implements IExpression {
    String operator;
    IExpression left;
    IExpression right;

    public RelationalExpression(String operator, IExpression left, IExpression right){
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public IValue evaluate(IGenericDictionary<String, IValue> table, IHeap myHeap) throws AppExceptions {
        IValue leftValue = left.evaluate(table, myHeap);
        IValue rightValue = right.evaluate(table, myHeap);
        if( leftValue.getType().equals(rightValue.getType()) && leftValue.getType().equals(new IntType())){
            int leftValueInt = ((IntValue)leftValue).getValue();
            int rightValueInt = ((IntValue)rightValue).getValue();
            return switch (operator) {
                case "==" -> new BoolValue(leftValueInt == rightValueInt);
                case "!=" -> new BoolValue(leftValueInt != rightValueInt);
                case "<" -> new BoolValue(leftValueInt < rightValueInt);
                case ">" -> new BoolValue(leftValueInt > rightValueInt);
                case ">=" -> new BoolValue(leftValueInt >= rightValueInt);
                case "<=" -> new BoolValue(leftValueInt <= rightValueInt);
                default -> throw new AppExceptions("operator not supported");
            };
        }
        else{
            throw new TypeMismatch("both operators should be of type int");
        }
    }

    @Override
    public IType typecheck(IGenericDictionary<String, IType> dict) throws AppExceptions {
        IType typeOfLeft = left.typecheck(dict);
        IType typeOfRight = right.typecheck(dict);

        if( !typeOfLeft.equals(new IntType())){
            throw new TypeMismatch("left operand is not an integer");
        }
        if( !typeOfRight.equals(new IntType())){
            throw new TypeMismatch("right operand is not an integer");
        }

        return new BoolType();
    }

    @Override
    public IExpression deepcopy() {
        return new  RelationalExpression(operator, left.deepcopy(), right.deepcopy());
    }

    @Override
    public String toString(){
        return left.toString() + operator + right.toString();
    }
}
