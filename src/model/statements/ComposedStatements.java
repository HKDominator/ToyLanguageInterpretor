package model.statements;

import model.adt.dictionary.IGenericDictionary;
import model.adt.exceptions.AppExceptions;
import model.state.PrgState;
import model.types.IType;

public class ComposedStatements implements IStatement {

    IStatement firstStatement;
    IStatement secondStatement;

    public ComposedStatements(IStatement firstStatement, IStatement secondStatement) {
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }

    @Override
    public PrgState execute(PrgState state) {
        state.getExecutionStack().push(secondStatement);
        state.getExecutionStack().push(firstStatement);
        return null;
    }

    @Override
    public IGenericDictionary<String, IType> typecheck(IGenericDictionary<String, IType> typeDictionary) throws AppExceptions {
        return secondStatement.typecheck(firstStatement.typecheck(typeDictionary));
    }

    @Override
    public String toString()
    {
        return firstStatement.toString() + ";\n" +  secondStatement.toString();
    }

    @Override
    public IStatement deepCopy() {
        return new ComposedStatements(firstStatement.deepCopy(), secondStatement.deepCopy());
    }

}
