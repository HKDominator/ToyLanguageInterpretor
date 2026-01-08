package model.statements;

import model.adt.dictionary.IGenericDictionary;
import model.adt.exceptions.AppExceptions;
import model.adt.heap.IHeap;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.IType;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.IOException;

public class CloseReadFile implements IStatement{
    IExpression expression;

    public CloseReadFile(IExpression expression){
        this.expression = expression;
    }
    @Override
    public PrgState execute(PrgState state) throws AppExceptions {
        IHeap heap = state.getMyHeap();
        IValue evaluatedExpression = expression.evaluate(state.getSymTable(), heap);
        if( !evaluatedExpression.getType().equals(new StringType())){
            throw new AppExceptions("File name is not a string!");
        }

        String filename = ((StringValue)evaluatedExpression).getValue();
        if( state.getFileTable().get(filename) == null){
            throw new AppExceptions("File does not exist!");
        }
        try{
            state.getFileTable().get(filename).close();
        }catch(IOException error)
        {
            throw new AppExceptions(error.getMessage());
        }
        state.getFileTable().remove(filename);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseReadFile(expression.deepcopy());
    }

    @Override
    public IGenericDictionary<String, IType> typecheck(IGenericDictionary<String, IType> typeDictionary) throws AppExceptions {
        IType typeOfExpression = expression.typecheck(typeDictionary);
        if( typeOfExpression.equals(new StringType()) ){
            return typeDictionary;
        }
        throw new AppExceptions("close the statement: the name of the file is not a string");
    }

    @Override
    public String toString(){
        return "close(" + expression.toString() + ")";
    }
}
