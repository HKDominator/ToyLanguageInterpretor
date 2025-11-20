package controller;

import model.adt.exceptions.AppExceptions;
import model.adt.stack.IGenericStack;
import model.state.PrgState;
import model.statements.IStatement;
import model.values.IValue;
import model.values.IntValue;
import model.values.ReferenceValue;
import repository.IRepo;

import java.util.*;
import java.util.stream.Collectors;

public class Controller implements IController {
    private IRepo repo;

    public Controller(IRepo repo) {
        this.repo = repo;
    }

    @Override
    public PrgState doOneStep(PrgState prgState) throws AppExceptions {
        IGenericStack<IStatement> executionStack = prgState.getExecutionStack();
        if (executionStack.isEmpty())
            throw new AppExceptions("The execution stack is empty");
        IStatement statement = executionStack.pop();
        return statement.execute(prgState);
    }

    Map<Integer, IValue> garbageCollector(List<Integer> addressesFromSymTable, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(entry -> addressesFromSymTable.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAllAddresses(Collection<IValue> symbolTableValues, Map<Integer, IValue> heap) {
        List<Integer> listOfAddresses = new LinkedList<Integer>();
        symbolTableValues.stream()
                .filter(value -> value instanceof ReferenceValue)
                .forEach( value -> {
                            while (value instanceof ReferenceValue) {
                                listOfAddresses.add(((ReferenceValue)value).getaddress());
                                value = heap.get(((ReferenceValue)value).getaddress());
                            }
                        }
                );
        return listOfAddresses;
    }

    @Override
    public void doAllSteps(boolean flag) throws AppExceptions {
        PrgState programState = repo.getCurrentProgram();
        if(flag)
            repo.logProgramStateToLogFile();
        while( programState.getExecutionStack().isEmpty() == false) {
            try {
                programState = doOneStep(programState);
                if( flag )
                    repo.logProgramStateToLogFile();
                programState.getMyHeap().setContent(
                        garbageCollector(
                            getAllAddresses(
                                    programState.getSymTable().getValues(),
                                    programState.getMyHeap().getContent()
                            ),
                            programState.getMyHeap().getContent()
                        )
                );
            }catch(AppExceptions e){
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public PrgState getPrgState() {
        return repo.getCurrentProgram();
    }
}
