package model.state;

import model.adt.dictionary.IGenericDictionary;
import model.adt.exceptions.AppExceptions;
import model.adt.exceptions.EmptyStack;
import model.adt.filetable.FileTable;
import model.adt.list.IGenericList;
import model.adt.stack.IGenericStack;
import model.statements.IStatement;
import model.values.IValue;

public class PrgState {
    private IGenericStack<IStatement> executionStack;
    private IGenericDictionary<String, IValue> symTable;
    private IGenericList<IValue> output;
    private IStatement initialStatement;
    private FileTable fileTable;

    public PrgState(IGenericStack<IStatement> executionStack, IGenericDictionary<String, IValue> symTable, IGenericList<IValue> output, FileTable fileTable, IStatement statement) {
        this.executionStack = executionStack;
        this.symTable = symTable;
        this.output = output;
        this.initialStatement = statement.deepCopy();
        this.executionStack.push(statement);
        this.fileTable =  fileTable;
    }

    public void setExecutionStack(IGenericStack<IStatement> executionStack) {
        this.executionStack = executionStack;
    }

    public void setSymTable(IGenericDictionary<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public void setOutput(IGenericList<IValue> output) {
        this.output = output;
    }

    public IGenericStack<IStatement> getExecutionStack() {
        return executionStack;
    }

    public IGenericDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public FileTable getFileTable() {
        return fileTable;
    }

    public IStatement getInitialStatement() {
        return initialStatement;
    }

    public IGenericList<IValue> getOutput() {
        return output;
    }

    public boolean isCompleted()
    {
        return this.executionStack.isEmpty();
    }

    @Override
    public String toString()
    {
        StringBuilder string = new StringBuilder();
        string.append("Program state\n");
        string.append("Execution stack:\n").append(executionStack.toString()).append("\n");
        string.append("Sym table:\n").append(symTable.toString()).append("\n");
        string.append("Output state:\n").append(output.toString()).append("\n");
        string.append("File table:\n").append(fileTable.toString()).append("\n");
        return string.toString();
    }

    public PrgState executeOneStep() throws AppExceptions
    {
        if( executionStack.isEmpty() )
        {
            throw new EmptyStack("There is no statement to execute");
        }
        IStatement statement = this.executionStack.pop();
        return statement.execute(this);
    }


}
