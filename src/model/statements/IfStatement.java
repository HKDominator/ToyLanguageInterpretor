package model.statements;

import model.adt.dictionary.IGenericDictionary;
import model.adt.exceptions.AppExceptions;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;

public class IfStatement implements IStatement{

    private IExpression condition;
    private IStatement thenStatement;
    private IStatement elseStatement;

    public IfStatement(IExpression condition, IStatement thenStatement, IStatement elseStatement) {
        this.condition = condition;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public PrgState execute(PrgState state) throws AppExceptions {
        if( !condition.evaluate(state.getSymTable()).getType().equals(new BoolType())){
            throw new AppExceptions("condition is not of type boolean");
        }
        if(((BoolValue) condition.evaluate(state.getSymTable())).getValue())
        {
            state.getExecutionStack().push(thenStatement);
        }
        else
        {
            state.getExecutionStack().push(elseStatement);
        }
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(condition.deepcopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }

    @Override
    public IGenericDictionary<String, IType> typecheck(IGenericDictionary<String, IType> typeDictionary) throws AppExceptions {
        IType typeOfCondition = condition.typecheck(typeDictionary);
        if( !(typeOfCondition.equals(new BoolType()))){
            throw new AppExceptions("condition is not of type boolean");
        }
        thenStatement.typecheck(typeDictionary.deepCopy());
        elseStatement.typecheck(typeDictionary.deepCopy());
        return typeDictionary;
    }

    @Override
    public String toString()
    {
        return "if (" + condition.toString() + ") then {" + thenStatement.toString() + "} else {" + elseStatement.toString() + "}";
    }

}
