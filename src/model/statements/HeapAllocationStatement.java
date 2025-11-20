package model.statements;

import model.adt.dictionary.IGenericDictionary;
import model.adt.exceptions.AppExceptions;
import model.adt.heap.IHeap;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.IType;
import model.types.ReferenceType;
import model.values.ReferenceValue;

public class HeapAllocationStatement implements IStatement {
    private String variableName;
    private IExpression expression;

    public HeapAllocationStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws AppExceptions {
        if( state.getSymTable().lookup(variableName) == null )
            throw new AppExceptions("Variable " + variableName + " not found");
        if( !(state.getSymTable().lookup(variableName).getType() instanceof ReferenceType) )
            throw new AppExceptions("Variable " + variableName + " is not a reference");

        IHeap myHeap = state.getMyHeap();
        IType typeOfTheExpression = expression.evaluate(state.getSymTable(), myHeap).getType();
        IType typeOfVariablePointedAt = ((ReferenceValue)state.getSymTable().lookup(variableName)).getLocationType();
        if( !typeOfTheExpression.equals(typeOfVariablePointedAt)){
            throw new AppExceptions("the type of the expression does not match the type of the reference");
        }
        myHeap.put(expression.evaluate(state.getSymTable(),myHeap));

        state.getSymTable().insert(variableName, new ReferenceValue(myHeap.getLastAddressGenerated(),expression.evaluate(state.getSymTable(),myHeap).getType()));
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapAllocationStatement(variableName,  expression.deepcopy());
    }

    @Override
    public IGenericDictionary<String, IType> typecheck(IGenericDictionary<String, IType> typeDictionary) throws AppExceptions {
        IType typeOfExpression = expression.typecheck(typeDictionary);
        IType typeOfVariablePointedAt = typeDictionary.lookup(variableName);

        if( typeOfVariablePointedAt.equals(new ReferenceType(typeOfExpression))){
            return typeDictionary;
        }
        throw new AppExceptions("heap allocations statement: type of right hand side and left hand side have different types");
    }

    @Override
    public String toString() {
        return "new(" + variableName + ", " + expression.toString() + ")";
    }
}
