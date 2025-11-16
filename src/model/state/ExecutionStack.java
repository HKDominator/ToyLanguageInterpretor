package model.state;

import model.adt.exceptions.AppExceptions;
import model.adt.stack.GenericStack;
import model.adt.stack.IGenericStack;
import model.adt.exceptions.StackEmptyException;
import model.statements.IStatement;

import java.util.List;

public class ExecutionStack implements IExecutionStack {

    IGenericStack<IStatement> stack;

    public ExecutionStack() {
        this.stack = new GenericStack<>();
    }

    @Override
    public IStatement pop() throws StackEmptyException {
        return stack.pop();
    }

    @Override
    public void push(IStatement statement) {
        stack.push(statement);
    }

    @Override
    public boolean empty() {
        return stack.isEmpty();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public String toString() {
        StringBuilder print = new StringBuilder();
        IGenericStack<IStatement> tmpStack = new GenericStack<>();
        try{
            while( !stack.isEmpty()){
                tmpStack.push(stack.pop());
                print.append(tmpStack.top().toString()).append("\n");
            }
            while(!tmpStack.isEmpty()){
                stack.push(tmpStack.pop());
            }
        } catch( AppExceptions exception)
        {
            throw new RuntimeException(exception.getMessage());
        }
        return print.toString();
    }

    @Override
    public List<IStatement> toList() {
        return stack.toList();
    }
}
