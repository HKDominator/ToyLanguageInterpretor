package repository;

import model.adt.dictionary.GenericDictionary;
import model.adt.dictionary.IGenericDictionary;
import model.adt.exceptions.AppExceptions;
import model.adt.filetable.FileTable;
import model.adt.heap.Heap;
import model.adt.heap.IHeap;
import model.adt.list.GenericList;
import model.adt.list.IGenericList;
import model.adt.stack.GenericStack;
import model.adt.stack.IGenericStack;
import model.state.PrgState;
import model.statements.IStatement;
import model.types.IType;
import model.values.IValue;

import javax.management.InvalidApplicationException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repo implements IRepo{
    private List<PrgState> listOfPrograms;
    private int progamIndex;
    private String logFilePath;
    public Repo(IStatement program, String logFilePath) {
        this.listOfPrograms = new ArrayList<PrgState>();
        this.logFilePath = logFilePath;
        addProgram(program);
        progamIndex = 0;
    }

    @Override
    public List<PrgState> getListOfPrograms() {
        return listOfPrograms;
    }

    @Override
    public void setListOfPrograms(List<PrgState> listOfPrograms) {
        this.listOfPrograms = listOfPrograms;
    }

    @Override
    public void addProgram(IStatement statement) {
        IGenericStack<IStatement> executionStack = new GenericStack<IStatement>();
        IGenericDictionary<String, IValue> symbolTable = new GenericDictionary<String, IValue>();
        IGenericList<IValue> outputOfProgram = new GenericList<IValue>();
        FileTable fileTable = new FileTable();
        IHeap myHeap =  new Heap();
        try{
            statement.typecheck(new GenericDictionary<String, IType>());
        }catch(AppExceptions e){
            System.out.println(e.getMessage());
            return;
        }
        PrgState programState = new PrgState(executionStack, symbolTable, outputOfProgram, fileTable, myHeap, statement);
        listOfPrograms.add(programState);
    }

    @Override
    public int size() {
        return listOfPrograms.size();
    }

    @Override
    public PrgState getCurrentProgram() {
        return listOfPrograms.get(progamIndex);
    }

    @Override
    public void logProgramStateToLogFile() throws AppExceptions {
        PrgState state = getCurrentProgram();
        try(PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))){
            logFile.println(state.toString());
        }catch(IOException error)
        {
            throw new AppExceptions(error.getMessage());
        }
    }
}
