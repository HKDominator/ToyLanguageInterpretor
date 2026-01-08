package controller;

import model.adt.exceptions.AppExceptions;
import model.state.PrgState;

import java.util.List;

public interface IController {
    void doAllSteps() throws AppExceptions;
    PrgState getPrgState();
    List<PrgState> removeCompletedPrograms(List<PrgState> inProgramList) throws AppExceptions;
    void oneStepForAllPrograms(List<PrgState> programList) throws AppExceptions;
}
