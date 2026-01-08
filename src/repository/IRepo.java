package repository;

import model.adt.exceptions.AppExceptions;
import model.expressions.IExpression;
import model.state.PrgState;
import model.statements.IStatement;
import model.statements.IfStatement;

import java.util.List;

public interface IRepo {
    public List<PrgState> getListOfPrograms();
    public void setListOfPrograms(List<PrgState> listOfPrograms);
    void addProgram(IStatement state) throws AppExceptions;
    int size();
    PrgState getCurrentProgram();
    void logProgramStateToLogFile(PrgState ps) throws AppExceptions;
}
