package model.statements;

import model.adt.dictionary.IGenericDictionary;
import model.adt.exceptions.AppExceptions;
import model.adt.exceptions.NotDeclaredExpression;
import model.adt.exceptions.TypeMismatch;
import model.adt.heap.IHeap;
import model.adt.stack.IGenericStack;
import model.expressions.IExpression;
import model.state.PrgState;
import model.values.IValue;
import model.types.IType;

public class AssignmentStatement implements IStatement {
    private String idOfVariable;
    private IExpression expressionToBeAssigned;

    public AssignmentStatement(String idOfVariable, IExpression expressionToBeAssigned) {
        this.idOfVariable = idOfVariable;
        this.expressionToBeAssigned = expressionToBeAssigned;
    }

    @Override
    public PrgState execute(PrgState state) throws AppExceptions {
        IGenericDictionary<String, IValue> symbolTable = state.getSymTable();
        IGenericStack<IStatement> executionStack = state.getExecutionStack();
        IHeap heap = state.getMyHeap();

        if( symbolTable.is_defined(idOfVariable) ) {
            IValue value = expressionToBeAssigned.evaluate(symbolTable, heap);
            IType typeOfVariable = symbolTable.lookup(idOfVariable).getType();
            if( value.getType().equals(typeOfVariable) ) {
                symbolTable.insert(idOfVariable, value);
            }
            else {
                throw new TypeMismatch("declared type of variable " + idOfVariable + " and type of " +
                        "the assigned expression do not match");
            }
        }
        else{
            throw new NotDeclaredExpression("the used expression " + idOfVariable + " was not declared before");
        }
        return state;
    }

    @Override
    public IGenericDictionary<String, IType> typecheck(IGenericDictionary<String, IType> typeOfEachVariables) throws AppExceptions {
        IType typeOfVariable = typeOfEachVariables.lookup(idOfVariable);
        IType typeOfExpression =  expressionToBeAssigned.typecheck(typeOfEachVariables);
        if( !typeOfVariable.equals(typeOfExpression) ) {
            throw new TypeMismatch("Assignent: right hand side and left hand side \n" + "have different types");
        }
        return typeOfEachVariables;
    }

    @Override
    public String toString()
    {
        return idOfVariable + "=" + expressionToBeAssigned.toString();
    }

    @Override
    public IStatement deepCopy() {
        return new AssignmentStatement(idOfVariable, expressionToBeAssigned.deepcopy());
    }

}
