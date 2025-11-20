package model.statements;

import model.adt.dictionary.IGenericDictionary;
import model.adt.exceptions.AppExceptions;
import model.adt.heap.IHeap;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.IType;
import model.types.IntType;
import model.types.StringType;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStatement {
    private IExpression fileNameExpression;
    private String variableName;

    public ReadFile(IExpression expression, String variableName) {
        this.fileNameExpression = expression;
        this.variableName = variableName;
    }

    @Override
    public PrgState execute(PrgState state) throws AppExceptions {
        IValue value = state.getSymTable().lookup(variableName);
        if( value == null){
           throw new AppExceptions("the variable " + variableName + " was not declared before");
       }
       if( !value.getType().equals(new IntType())){
           throw new AppExceptions("the variable " + variableName + " is not of type int");
       }
       IHeap heap = state.getMyHeap();
       IValue fileNameValue = fileNameExpression.evaluate(state.getSymTable(), heap);
       if( !fileNameValue.getType().equals(new StringType()))
           throw new AppExceptions("the variable " + variableName + " is not of type string");

       String fileNameAsString = ((StringValue)fileNameValue).getValue();
       BufferedReader fileDescriptor = state.getFileTable().get(fileNameAsString);
       if( fileDescriptor == null){
           throw new AppExceptions("the file " + fileNameAsString + " was not found");
       }
       String line;
       try{
           line = fileDescriptor.readLine();
       }catch(IOException error)
       {
           throw new AppExceptions(error.getMessage());
       }

       IntValue readValue;
       if( line == null )
       {
           readValue = new IntValue();
       }
       else{
           readValue = new IntValue(Integer.parseInt(line));
       }
       state.getSymTable().insert(variableName, readValue);
       return state;
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFile(fileNameExpression.deepcopy(), variableName);
    }

    @Override
    public IGenericDictionary<String, IType> typecheck(IGenericDictionary<String, IType> typeDictionary) throws AppExceptions {
        IType typeOfExpression = fileNameExpression.typecheck(typeDictionary);
        IType typeOfVariable = typeDictionary.lookup(variableName);
        if( !typeOfExpression.equals(new StringType())) {
            throw new AppExceptions("read number from file statement: the name of the file is not a string!");
        }
        if( !typeOfVariable.equals(new IntType())){
            throw new AppExceptions("read number from file statement: the name of the variable is not a int!");
        }
        return typeDictionary;
    }

    @Override
    public String toString(){
        return "Read from " + fileNameExpression.toString() + " into " + variableName;
    }
}
