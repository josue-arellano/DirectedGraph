/*************************************************************** 
*   file: ListInterface.java 
*   author: J. Arellano 
*   class: CS 241 – Data Structures and Algorithms II
* 
*   assignment: program 3
*   date last modified: 3/8/2018 
* 
*   purpose: This file outlines what a methods a List class should implement.
* 
****************************************************************/
package directedgraph;

public interface ListInterface<T>
{
    public void add(T newEntry);
    public void add(int newPosition, T newEntry);
    public T remove(int givenPosition);
    public void clear();
    public T replace(int givenPosition, T newEntry);
    public T getEntry(int givenPosition);
    public T[] toArray();
    public boolean contains(T anEntry);
    public int getLength();
    public boolean isEmpty();
}
