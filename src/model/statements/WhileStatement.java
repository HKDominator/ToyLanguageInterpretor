package model.statements;

import model.adt.dictionary.IGenericDictionary;
import model.adt.exceptions.AppExceptions;
import model.adt.stack.GenericStack;
import model.adt.stack.IGenericStack;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;

public class WhileStatement implements IStatement {
    IExpression condition;
    IStatement body;

    public WhileStatement(IExpression condition, IStatement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public PrgState execute(PrgState state) throws AppExceptions {
        if( !((condition.evaluate(state.getSymTable(), state.getMyHeap()) instanceof BoolValue) )){
            throw new AppExceptions("condition was not evaluated to a bool value");
        }
        if ( ((BoolValue) condition.evaluate(state.getSymTable(), state.getMyHeap())).getValue()){
            IGenericStack<IStatement> stack = state.getExecutionStack();
            stack.push(this.deepCopy());
            stack.push(this.body.deepCopy());
        }
        return  state;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(condition.deepcopy(), body.deepCopy());
    }

    @Override
    public IGenericDictionary<String, IType> typecheck(IGenericDictionary<String, IType> typeDictionary) throws AppExceptions {
        IType expressionType = condition.typecheck(typeDictionary);
        if( !expressionType.equals(new BoolType()) ){
            throw new AppExceptions("expression type is not a bool type");
        }
        body.typecheck(typeDictionary.deepCopy());
        return typeDictionary;
    }

    @Override
    public String toString() {
        return "While(" + condition.toString() + "){" + body.toString() + "}";
    }
}
