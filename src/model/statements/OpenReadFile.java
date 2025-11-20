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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class OpenReadFile implements IStatement{

    private IExpression expression;

    public OpenReadFile(IExpression expression){
        this.expression = expression;
    }
    @Override
    public PrgState execute(PrgState state) throws AppExceptions {
        IHeap heap = state.getMyHeap();
        IValue evaluatedExpression = expression.evaluate(state.getSymTable(), heap);
        if( evaluatedExpression.getType().equals(new StringType()))
        {
            String fileName = ((StringValue)evaluatedExpression).getValue();
            if( state.getFileTable().get(fileName) == null )
            {
                try{
                    BufferedReader fileDescriptor = new BufferedReader(new FileReader(fileName));
                    state.getFileTable().put(fileName, fileDescriptor);
                }catch(IOException error){
                    throw new AppExceptions(error.getMessage());
                }
            }else{
                throw new AppExceptions("The file already exists");
            }
        }else{
            throw new AppExceptions("The file name is not a string!");
        }
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new OpenReadFile(expression.deepcopy());
    }

    @Override
    public IGenericDictionary<String, IType> typecheck(IGenericDictionary<String, IType> typeDictionary) throws AppExceptions {
        IType typeOfExpression = expression.typecheck(typeDictionary);
        if( typeOfExpression.equals(new StringType()) ){
            return typeDictionary;
        }
        throw new AppExceptions("open file statement: the name of the file is not a string");
    }

    @Override
    public String toString(){
        return "Open(" + expression.toString() + ")";
    }
}
