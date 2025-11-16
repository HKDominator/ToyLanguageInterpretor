package model.statements;

import model.adt.dictionary.IGenericDictionary;
import model.adt.exceptions.AppExceptions;
import model.adt.exceptions.NotDeclaredExpression;
import model.state.PrgState;
import model.types.IType;

public interface IStatement {
    PrgState execute(PrgState state) throws AppExceptions ;

    String toString();
    IStatement deepCopy();

    IGenericDictionary<String, IType> typecheck(IGenericDictionary<String, IType> typeDictionary) throws AppExceptions;
}
