package controller;

import model.adt.exceptions.AppExceptions;
import model.state.PrgState;

public interface IController {
    PrgState doOneStep(PrgState prgState) throws AppExceptions;
    void doAllSteps(boolean flag) throws AppExceptions;
    PrgState getPrgState();
}
