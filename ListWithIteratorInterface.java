/*************************************************************** 
*   file: ListWithIteratorInterface.java 
*   author: J. Arellano 
*   class: CS 241 – Data Structures and Algorithms II
* 
*   assignment: program 3
*   date last modified: 3/8/2018 
* 
*   purpose: This file outlines what a methods a ListWithIterator class should
*            implement.
* 
****************************************************************/
package directedgraph;
import java.util.Iterator;

public interface ListWithIteratorInterface<T> extends ListInterface<T>, Iterable
{
    public Iterator<T> getIterator();
}
