/*************************************************************** 
*   file: Vertex.java 
*   author: J. Arellano 
*   class: CS 241 – Data Structures and Algorithms II
* 
*   assignment: program 3
*   date last modified: 3/8/2018 
* 
*   purpose: This file outlines the methods needed to create a graph.
* 
****************************************************************/
package directedgraph;
import java.util.Stack;

public interface GraphInterface<T, V>
{
    public V getLabel(T vertexKey);
    public boolean addVertex(T vertexKey, V label);
    public boolean addEdge(T begin, T end, double edgeWeight);
    public boolean addEdge(T begin, T end);
    public boolean hasEdge(T begin, T end);
    public boolean removeEdge(T begin, T end);
    public boolean isEmpty();
    public int getNumberOfVertices();
    public double getCheapestPath(T origin, T end, Stack<VertexInterface<V>> path);
    public int getNumberOfEdges();
}
