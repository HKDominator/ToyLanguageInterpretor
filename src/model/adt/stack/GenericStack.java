package model.adt.stack;

import model.adt.exceptions.StackEmptyException;

import java.util.List;
import java.util.Stack;

public class GenericStack<T> implements IGenericStack<T> {
    Stack<T> stack;

    public GenericStack()
    {
        this.stack = new Stack<>();
    }

    @Override
    public void push(T item)
    {
        this.stack.push(item);
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public T top() throws StackEmptyException {
        if( this.stack.isEmpty() )
            throw new StackEmptyException("Stack is empty");
        return this.stack.peek();
    }

    @Override
    public T pop() throws StackEmptyException {
        if(this.stack.isEmpty())
            throw new StackEmptyException("Stack is empty");
        return  this.stack.pop();
    }

    @Override
    public int size() {
        return this.stack.size();
    }

    @Override
    public List<T> toList() {
        return stack.stream().toList();
    }

    public Stack<T> getStack()
    {
        return stack;
    }

    public void setStack(Stack<T> stack)
    {
        this.stack = stack;
    }

    @Override
    public String toString()
    {
        StringBuilder print = new StringBuilder();
        for( T element : this.stack )
        {
            print.append(element.toString()).append('\n');
        }
//        if(!print.isEmpty())
//            print.setLength(print.length()-1);
        return print.toString() ;
    }

}
