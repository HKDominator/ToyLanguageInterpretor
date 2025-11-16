package model.state;

import model.adt.exceptions.StackEmptyException;
import model.statements.IStatement;

import java.util.List;

public interface IExecutionStack {

    IStatement pop() throws StackEmptyException;

    void push(IStatement statement);

    boolean empty();

    int size();

    String toString();

    List<IStatement> toList();
}
