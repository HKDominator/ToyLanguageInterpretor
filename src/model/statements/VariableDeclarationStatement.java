package model.statements;

import model.adt.dictionary.IGenericDictionary;
import model.adt.exceptions.AppExceptions;
import model.state.PrgState;
import model.types.IType;

public class VariableDeclarationStatement implements IStatement {
    private String variableName;
    private IType variableType;

    public VariableDeclarationStatement(String variableName, IType variableType) {
        this.variableName = variableName;
        this.variableType = variableType;
    }

    @Override
    public PrgState execute(PrgState state) throws AppExceptions {
        if( state.getSymTable().lookup(variableName) != null ) {
            throw new AppExceptions("Variable already declared");
        }
        state.getSymTable().insert(variableName, variableType.getDefaultValue());
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new VariableDeclarationStatement(this.variableName, this.variableType.deepCopy());
    }

    @Override
    public IGenericDictionary<String, IType> typecheck(IGenericDictionary<String, IType> typeDictionary) throws AppExceptions {
        typeDictionary.insert(variableName, variableType);
        return typeDictionary;
    }

    @Override
    public String toString()
    {
        return variableType.toString()+ " " + variableName;
    }

}
