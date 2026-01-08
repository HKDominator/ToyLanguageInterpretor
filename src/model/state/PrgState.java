package model.state;

import model.adt.dictionary.IGenericDictionary;
import model.adt.exceptions.AppExceptions;
import model.adt.exceptions.EmptyStack;
import model.adt.filetable.FileTable;
import model.adt.heap.Heap;
import model.adt.heap.IHeap;
import model.adt.list.IGenericList;
import model.adt.stack.IGenericStack;
import model.statements.IStatement;
import model.values.IValue;

import java.util.concurrent.atomic.AtomicInteger;

public class PrgState {
    private IGenericStack<IStatement> executionStack;
    private IGenericDictionary<String, IValue> symTable;
    private IGenericList<IValue> output;
    private final IStatement initialStatement;
    private final FileTable fileTable;
    private final IHeap myHeap;
    private static final AtomicInteger lastid = new AtomicInteger(-1);
    private final Integer id;

    public PrgState(IGenericStack<IStatement> executionStack, IGenericDictionary<String, IValue> symTable, IGenericList<IValue> output, FileTable fileTable, IHeap myHeap, IStatement statement) {
        this.executionStack = executionStack;
        this.symTable = symTable;
        this.output = output;
        this.initialStatement = statement.deepCopy();
        this.executionStack.push(statement);
        this.fileTable =  fileTable;
        this.myHeap = myHeap;
        this.id = lastid.incrementAndGet();
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

    public IHeap getMyHeap() {
        return this.myHeap;
    }

    public Integer getId() {
        return id;
    }

    public IStatement getInitialStatement() {
        return initialStatement;
    }

    public IGenericList<IValue> getOutput() {
        return output;
    }

    public boolean isNotCompleted(){
        return !executionStack.isEmpty();
    }

    @Override
    public String toString()
    {
        String string = id.toString() + '\n' +
                "Program state\n" +
                "Execution stack:\n" + executionStack.toString() + "\n" +
                "Sym table:\n" + symTable.toString() + "\n" +
                "Output state:\n" + output.toString() + "\n" +
                "Heap:\n" + myHeap.toString() + "\n" +
                "File table:\n" + fileTable.toString() + "\n";
        return string;
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

    public PrgState doOneStep() throws AppExceptions {
        if (executionStack.isEmpty())
            throw new AppExceptions("The execution stack is empty");
        IStatement statement = executionStack.pop();
        return statement.execute(this);
    }
}
