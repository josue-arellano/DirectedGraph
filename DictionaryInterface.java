/**
 *   Name:      Arellano, Josue
 *   File:      DictionaryInterface.java
 *   Project:   #1
 *   Due:       Mar 5, 2018
 *   Course:    cs14103-w18
 *
 *   Description:
 *              This file outlines the files that a dictionary should implement.
 */
package directedgraph;
import java.util.Iterator;

public interface DictionaryInterface<K,V>
{
    public V add(K key, V value);
    public V getValue(K key);
    public boolean contains(K key);
    public Iterator<K> getKeyIterator();
    public Iterator<V> getValueIterator();
    public boolean isEmpty();
    public int getSize();
    public void clear();
}
