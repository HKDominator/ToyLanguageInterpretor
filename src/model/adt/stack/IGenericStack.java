package model.adt.stack;

import model.adt.exceptions.StackEmptyException;

import java.util.List;

public interface IGenericStack<T>{
    void push(T element);

    boolean isEmpty();

    T top() throws StackEmptyException;

    T pop() throws StackEmptyException;

    int size();

    public List<T> toList();
}
