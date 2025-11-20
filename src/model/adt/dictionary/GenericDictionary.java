package model.adt.dictionary;

import model.adt.exceptions.KeyNotFoundAppException;

import java.util.*;

public class GenericDictionary<K, V> implements IGenericDictionary<K, V> {

    private Hashtable<K, V> dictionary;

    public GenericDictionary() {
        this.dictionary = new Hashtable<K, V>();
    }

    public GenericDictionary(Hashtable<K, V> dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public void insert(K key, V value) {
        dictionary.put(key, value);
    }

    @Override
    public boolean is_defined(K key) {
        return dictionary.get(key) != null;
    }

    @Override
    public V lookup(K key) {
        return dictionary.get(key);
    }

    @Override
    public void delete(K key) throws KeyNotFoundAppException {
        if( !dictionary.containsKey(key) )
            throw  new KeyNotFoundAppException("Key " + key + " not found in the dictionary");
        dictionary.remove(key);
    }

    @Override
    public Hashtable<K, V> getDictionary() {
        return this.dictionary;
    }

    @Override
    public List<K> getKeyList() {
        return new ArrayList<>(this.dictionary.keySet());
    }

    @Override
    public boolean exists(K key) {
        return dictionary.containsKey(key);
    }

    @Override
    public IGenericDictionary<K, V> copy() {
        GenericDictionary<K, V> new_one = new GenericDictionary<>();
        for( K key : dictionary.keySet() ) {
            new_one.dictionary.put(key, this.dictionary.get(key));
        }
        return new_one;
    }

    @Override
    public int size() {
        return this.dictionary.size();
    }

    @Override
    public boolean isEmpty() {
        return this.dictionary.isEmpty();
    }

    @Override
    public IGenericDictionary<K, V> deepCopy() {
        return new GenericDictionary<K, V>((Hashtable<K,V>) dictionary.clone());
    }

    @Override
    public Collection<V> getValues() {
        return dictionary.values();
    }

    @Override
    public String toString() {
        StringBuilder print = new StringBuilder();
        for( K key : dictionary.keySet() ) {
            print.append(key.toString()).append(" -> ").append(dictionary.get(key).toString()).append(" \n");
        }
//        if(!print.isEmpty())
//            print.setLength(print.length()-2);
        return print.toString();
    }
}
