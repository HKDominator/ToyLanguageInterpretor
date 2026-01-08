package model.statements;

import model.adt.dictionary.IGenericDictionary;
import model.adt.exceptions.AppExceptions;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.IType;
import model.types.ReferenceType;
import model.values.IValue;
import model.values.ReferenceValue;

public class HeapWritingStatement implements IStatement{
    private String variableName;
    private IExpression newValue;

    public HeapWritingStatement(String variableName, IExpression expression){
        this.variableName = variableName;
        this.newValue = expression;
    }
    @Override
    public PrgState execute(PrgState state) throws AppExceptions {
//        if( state.getSymTable().lookup(variableName) == null )
//            throw new AppExceptions("Variable " + variableName + " not found");
        IValue val = state.getSymTable().lookup(variableName);
        if (val == null)
            throw new AppExceptions("Variable " + variableName + " not found");
        if( !(val.getType() instanceof ReferenceType ))
            throw new AppExceptions("Variable " + variableName + " is not of reference type");

        int addressOfVariable = ((ReferenceValue)val).getaddress();
        if( state.getMyHeap().get(addressOfVariable) == null )
            throw new AppExceptions("the address of the variable is not a key in the heap");

        IType locationTypeOfVariable = ((ReferenceValue)state.getSymTable().lookup(variableName)).getLocationType();
        IType typeOfNewValue = newValue.evaluate(state.getSymTable(), state.getMyHeap()).getType();

        if( !typeOfNewValue.equals(locationTypeOfVariable) )
            throw new AppExceptions("The heap location and the new value must be of the same type");

        state.getMyHeap().changeValue(addressOfVariable, newValue.evaluate(state.getSymTable(), state.getMyHeap()));

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapWritingStatement(variableName, newValue.deepcopy());
    }

    @Override
    public IGenericDictionary<String, IType> typecheck(IGenericDictionary<String, IType> typeDictionary) throws AppExceptions {
        IType typeOfExpression = newValue.typecheck(typeDictionary);
        IType typeOfVariable = typeDictionary.lookup(variableName);

        if( typeOfVariable.equals(new ReferenceType(typeOfExpression)))
            return typeDictionary;

        throw new AppExceptions("heap writing statement: right hand side and " +
        "left hand side have different types");
    }

    @Override
    public String toString(){
        return "writeHeap(" +  variableName +"," + newValue.toString() + ")";
    }
}
