package model.statements;

import model.adt.dictionary.IGenericDictionary;
import model.adt.exceptions.AppExceptions;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.IType;

public class PrintStatements implements IStatement {

    private IExpression expression;

    public PrintStatements(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws AppExceptions {
        state.getOutput().add(expression.evaluate(state.getSymTable()));
        return state;
    }

    @Override
    public IGenericDictionary<String, IType> typecheck(IGenericDictionary<String, IType> typeDictionary) throws AppExceptions {
        expression.typecheck(typeDictionary);
        return typeDictionary;
    }

    @Override
    public String toString()
    {
        return "print(" + expression.toString() + ")";
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatements(expression.deepcopy());
    }
}
