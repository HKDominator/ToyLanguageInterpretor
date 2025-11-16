package model.adt.dictionary;

import model.adt.exceptions.KeyNotFoundAppException;

import java.util.Hashtable;
import java.util.List;

public interface    IGenericDictionary<K, V> {
    void insert(K key, V value);
    boolean is_defined(K key);
    V lookup(K key);
    void delete(K key) throws KeyNotFoundAppException;
    Hashtable<K, V> getDictionary();
    List<K> getKeyList();
    boolean exists(K key);
    IGenericDictionary<K, V> copy();
    int size();
    boolean isEmpty();
    IGenericDictionary<K, V> deepCopy();
}
