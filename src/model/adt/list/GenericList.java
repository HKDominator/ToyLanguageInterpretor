package model.adt.list;

import java.util.ArrayList;
import java.util.List;

public class GenericList<T> implements IGenericList<T>{
    private List<T> list;

    public GenericList(){
        list = new ArrayList<T>();
    }

    public void setOutput(List<T> list){
        this.list = list;
    }

    @Override
    public void add(T element) {
        this.list.add(element);
    }

    @Override
    public void clear() {
        this.list.clear();
    }

    @Override
    public List<T> getList() {
        return list;
    }

    @Override
    public String toString()
    {
        StringBuilder print = new StringBuilder();
        for( T element : list )
        {
            print.append(element.toString()).append(" ");
        }
        return print.toString();
    }

}
