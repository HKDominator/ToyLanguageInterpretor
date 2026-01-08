package controller;

import model.adt.exceptions.AppExceptions;
import model.adt.heap.IHeap;
import model.adt.stack.IGenericStack;
import model.state.PrgState;
import model.statements.IStatement;
import model.values.IValue;
import model.values.IntValue;
import model.values.ReferenceValue;
import repository.IRepo;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller implements IController {
    private final IRepo repo;
    private ExecutorService executor;

    public Controller(IRepo repo) {
        this.repo = repo;
    }

    Map<Integer, IValue> garbageCollector(List<Integer> addressesFromSymTable, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(entry -> addressesFromSymTable.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAllAddresses(List<Collection<IValue>> allSymbolTables, Map<Integer, IValue> heap) {
        List<Integer> listOfAddresses = new LinkedList<Integer>();
        allSymbolTables.forEach(symbolTable -> symbolTable.stream()
                .filter(value -> value instanceof ReferenceValue)
                .forEach( value -> {
                            while (value instanceof ReferenceValue) {
                                listOfAddresses.add(((ReferenceValue)value).getaddress());
                                value = heap.get(((ReferenceValue)value).getaddress());
                            }
                        }
                ));
        return listOfAddresses;
    }

    List<Integer> getWrongAddresses(Collection<IValue> symbolTableValues, Map<Integer, IValue> heap) {
        List<Integer> listOfAddresses = new LinkedList<Integer>();
        return symbolTableValues.stream()
                .filter(value -> value instanceof ReferenceValue)
                .map(value -> {ReferenceValue v1 = (ReferenceValue)value; return v1.getaddress();})
                .collect(Collectors.toList());
    }

    @Override
    public void doAllSteps() throws AppExceptions {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> programList = removeCompletedPrograms(repo.getListOfPrograms());
        while( programList.size() > 0 ){
            List<Collection<IValue>> allSymbolTables = new ArrayList<>();
            for( PrgState program : programList )
            {
                allSymbolTables.add(program.getSymTable().getValues());
            }

            IHeap heap = programList.getFirst().getMyHeap();
            heap.setContent(garbageCollector(
                    getAllAddresses(allSymbolTables, heap.getContent()),
                    heap.getContent()
            ));
            oneStepForAllPrograms(programList);
            programList = removeCompletedPrograms(repo.getListOfPrograms());
        }
        executor.shutdownNow();
        repo.setListOfPrograms(programList);
    }

    @Override
    public PrgState getPrgState() {
        return repo.getCurrentProgram();
    }

    @Override
    public List<PrgState> removeCompletedPrograms(List<PrgState> inProgressList) throws AppExceptions {
        // possibility return inProgressList.stream().filter(PrgState::isNotCompleted).collect(Collectors.toList());
        return inProgressList.stream().filter(PrgState -> PrgState.isNotCompleted()).collect(Collectors.toList());
    }

    @Override
    public void oneStepForAllPrograms(List<PrgState> programList) throws AppExceptions {
        programList.forEach(program -> {
            try {
                repo.logProgramStateToLogFile(program);
            } catch (AppExceptions e) {
                throw new RuntimeException(e);
            }
        }); // print the program state list to the log file
        List<Callable<PrgState>> callList = programList.stream()
                                            .map((PrgState state) -> (Callable<PrgState>)(() -> {return state.doOneStep();}))
                                            .collect(Collectors.toList()); // prepare the list of callables


        try {
            //start the exectuion of the callables

            //it returns the list of new created PrgStates threads
            List<PrgState> newProgramList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (ExecutionException e) {
                            if (e.getCause() instanceof AppExceptions) {
                                throw (AppExceptions) e.getCause();
                            } else {
                                throw new AppExceptions("Something is wrong: " + e.getMessage());
                            }
                        } catch (InterruptedException e) {
                            throw new AppExceptions("Thread was interrupted.");
                        } catch (CancellationException e) {
                            throw new AppExceptions("Thread was cancelleds.");
                        }
                    })
                    .filter(program -> program != null)
                    .collect(Collectors.toList());
            programList.addAll(newProgramList);
            programList.forEach(program -> repo.logProgramStateToLogFile(program)); //log in the file

            repo.setListOfPrograms(programList); // save the current programs
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
            throw new AppExceptions("Execution interrupted"+ e.getMessage());
        }

    }
}
