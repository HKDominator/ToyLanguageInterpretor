package model.adt.heap;

import model.adt.dictionary.IGenericDictionary;
import model.values.IValue;

import java.util.Map;

public interface IHeap {
    IValue get(int key);
    boolean isEmpty();
    IValue put(IValue value);
    IValue remove(int key);
    IValue changeValue(int address, IValue newValue);
    int getLastAddressGenerated();
    void setContent(Map<Integer,IValue> content);
    Map<Integer,IValue> getContent();
    void clear();
}
