package model.statements;

import model.adt.dictionary.IGenericDictionary;
import model.adt.exceptions.AppExceptions;
import model.state.PrgState;
import model.types.IType;

public class NonStatement implements IStatement {
    @Override
    public PrgState execute(PrgState state) throws AppExceptions {
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NonStatement();
    }

    @Override
    public IGenericDictionary<String, IType> typecheck(IGenericDictionary<String, IType> typeDictionary) throws AppExceptions {
        return typeDictionary;
    }

    @Override
    public String toString()
    {
        return "nop";
    }

}
