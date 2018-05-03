/*************************************************************** 
*   file: VertexInterface.java 
*   author: J. Arellano 
*   class: CS 241 – Data Structures and Algorithms II
* 
*   assignment: program 3
*   date last modified: 3/8/2018 
* 
*   purpose: This file outlines what a methods a vertex class should
*            implement.
* 
****************************************************************/
package directedgraph;
import java.util.Iterator;

public interface VertexInterface<T>
{
    public T getLabel();
    public void visit();
    public void unvisit();
    public boolean isVisited();
    public boolean disconnect(VertexInterface<T> endVertex);
    public boolean connect(VertexInterface<T> endVertex, double edgeWeight);
    public boolean connect(VertexInterface<T> endVertex);
    public Iterator<VertexInterface<T>> getNeighborIterator();
    public Iterator<Double> getWeightIterator();
    public boolean hasNeighbor();
    public String getCityCode();
    public boolean hasUnvisitedNeighbor();
    public VertexInterface<T> getUnvisitedNeighbor();
    public void setPredecessor(VertexInterface<T> predecessor);
    public VertexInterface<T> getPredecessor();
    public boolean hasPredecessor();
    public void setCost(double newCost);
    public double getCost();
}
