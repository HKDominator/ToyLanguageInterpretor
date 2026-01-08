package model.statements;

import model.adt.dictionary.IGenericDictionary;
import model.adt.exceptions.AppExceptions;
import model.adt.stack.GenericStack;
import model.adt.stack.IGenericStack;
import model.state.PrgState;
import model.types.IType;

import java.beans.Statement;
import java.util.Stack;

public class ForkStatement implements IStatement {
    private IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }
    @Override
    public PrgState execute(PrgState state) throws AppExceptions {
        GenericStack<IStatement> stack = new GenericStack<IStatement>();
        stack.push(statement);
        return new PrgState(stack, state.getSymTable().deepCopy(), state.getOutput(), state.getFileTable(), state.getMyHeap(), statement);
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public IGenericDictionary<String, IType> typecheck(IGenericDictionary<String, IType> typeDictionary) throws AppExceptions {
        statement.typecheck(typeDictionary.deepCopy());
        return typeDictionary;
    }

    @Override
    public String toString() {
        return "fork(" +  statement.toString() + ")";
    }
}
