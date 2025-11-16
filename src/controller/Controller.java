package controller;

import model.adt.exceptions.AppExceptions;
import model.adt.stack.IGenericStack;
import model.state.PrgState;
import model.statements.IStatement;
import repository.IRepo;

import java.util.Stack;

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
