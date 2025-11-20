package model.adt.heap;

import model.values.IValue;

import java.util.HashMap;
import java.util.Map;

public class Heap implements IHeap{
    private Map<Integer,IValue> content;
    private int NextFreeLocation;

    public Heap(){
        content = new HashMap<Integer,IValue>();
        NextFreeLocation = 0;
    }

    public int getNextFreeLocation(){
        NextFreeLocation++;
        return NextFreeLocation;
    }

    @Override
    public IValue get(int key) {
        return content.get(key);
    }

    @Override
    public boolean isEmpty() {
        return content.isEmpty();
    }

    @Override
    public IValue put(IValue value) {
        return content.put(getNextFreeLocation(), value);
    }

    @Override
    public IValue remove(int key) {
        return content.remove(key);
    }

    @Override
    public IValue changeValue(int address, IValue newValue) {
        return content.put(address, newValue);
    }

    @Override
    public int getLastAddressGenerated() {
        return NextFreeLocation;
    }

    @Override
    public void setContent(Map<Integer, IValue> newContent) {
        content.clear();
        content.putAll(newContent);
    }

    @Override
    public Map<Integer, IValue> getContent() {
        return content;
    }

    @Override
    public void clear() {
        content.clear();
    }

    @Override
    public String toString() {
        String heapAsString = "{";
        for( int key : content.keySet() ){
            heapAsString += key + "->" + content.get(key).toString() + "; ";
        }
        return heapAsString + '}';
    }
}
