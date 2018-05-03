/*************************************************************** 
*   file: Vertex.java 
*   author: J. Arellano 
*   class: CS 241 – Data Structures and Algorithms II
* 
*   assignment: program 3
*   date last modified: 3/8/2018 
* 
*   purpose: This file implements the VertexInterface interface.
*            this file is used to create vertices that are used to hold
*            city data and help us find shortest paths.
* 
****************************************************************/
package directedgraph;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Vertex<T> implements VertexInterface<T>, Comparable<T>
{
    private T label;
    private List<Edge> edgeList;
    private boolean visited;
    private VertexInterface<T> previousVertex;
    private double cost;
    
    public Vertex(T vertexLabel)
    {
        label = vertexLabel;
        edgeList = new ArrayList<Edge>();
        visited = false;
        previousVertex = null;
        cost = 0;
    }
    
    //method:  getCityCode
    //purpose: This method returns the city code of the vertex. The city code
    //         is found by looking in the label.
    public String getCityCode()
    {
        return label.toString().substring(4, 6);
    }
    
    //method:  compareTo
    //purpose: This method implents the Comparable method compareTo. This must
    //         be implemented in order to use the priority queue.
    public int compareTo(Object o)
    {
        Vertex<T> comp = (Vertex<T>) o;
        int result;
        if(this.cost < comp.cost)
            result = -1;
        if(this.cost > comp.cost)
            result = 1;
        else
            result = 0;
        return result;
    }
    
    //method:  getLabel
    //purpose: This method returns the label of the vertex.
    public T getLabel()
    {
        return label;
    }
    
    //method:  isVisited
    //purpose: This method returns the visited boolean.
    public boolean isVisited()
    {
        return visited;
    }
    
    //method:  unvisit
    //purpose: This method sets the visited field to false.
    public void unvisit()
    {
        visited = false;
    }
    
    //method:  visit
    //purpose: This method sets the visited field to true.
    public void visit()
    {
        visited = true;
    }
    
    //method:  hasPredecessor
    //purpose: This method returns true if the previousVertex field is not null
    //         and returns false if the previousVertex is null.
    public boolean hasPredecessor()
    {
        return previousVertex != null;
    }
    
    //method:  getPredecessor
    //purpose: This method returns the previousVertex.
    public VertexInterface<T> getPredecessor()
    {
        return previousVertex;
    }
    
    //method:  setPredecessor
    //purpose: This method sets the previousVertex to the VertexInterface<T>
    //         parameter.
    public void setPredecessor(VertexInterface<T> previousVertex)
    {
        this.previousVertex = previousVertex;
    }
    
    //method:  setCost
    //purpose: This method sets the cost field to the parameter.
    public void setCost(double newCost)
    {
        cost = newCost;
    }
    
    //method:  getCost
    //purpose: This method returns the cost field.
    public double getCost()
    {
        return cost;
    }
    
    protected class Edge
    {
        private VertexInterface<T> vertex;
        private double weight;
        
        protected Edge(VertexInterface<T> endVertex, double edgeWeight)
        {
            vertex = endVertex;
            weight = edgeWeight;
        }
        
        //method:  getEndVertex
        //purpose: This method returns the vertex field.
        protected VertexInterface<T> getEndVertex()
        {
            return vertex;
        }
        
        //method:  getWeight
        //purpose: This method returns weight field.
        protected double getWeight()
        {
            return weight;
        }
    }
    
    //method:  findEdge
    //purpose: This method looks for a neighbor of a vertex. First it checks if
    //         the vertex even has a neighbor. If it does not have a neighbor
    //         it will return null. Then it creates an iterator of the edgelist
    //         field to look through the neighbors to see if the desired vertex
    //         is a neighbor. If found it will return the edge. If not found it
    //         will return null.
    public Edge findEdge(VertexInterface<T> vertex)
    {
        if(hasNeighbor())
        {
            Iterator<Edge> edges = edgeList.iterator();
            while(edges.hasNext())
            {
                Edge nextEdge = edges.next();
                VertexInterface<T> nextNeighbor = nextEdge.getEndVertex();
                if(vertex.equals(nextNeighbor))
                    return nextEdge;
            }
        }
        return null;
    }
    
    //method:  disconnect
    //purpose: This method takes a vertex as an input and looks to see if the 
    //         endVertex is not the same vertex we are looking at. Then it will
    //         find the edge if any and then remove the edge from the edgeList
    //         list. If the edge between the vertex and endVertex is removed
    //         it will return true. Otherwise, it will return false.
    public boolean disconnect(VertexInterface<T> endVertex)
    {
        boolean result = false;
        
        if(!this.equals(endVertex))
        {
            Edge connection = findEdge(endVertex);
            result = edgeList.remove(connection);
        }
        
        return result;
    }
    
    //method:  connect
    //purpose: This method looks through the edgeList to find if the endVertex
    //         is not already a neighbor. If the endVertex is not a neighbor and
    //         is not the vertex we are looking at, then a new Edge is created 
    //         with the endVertex and edgeWeight and is added to the edgeList.
    //         If the edge was created then the method return true. Otherwise,
    //         it returns false.
    public boolean connect(VertexInterface<T> endVertex, double edgeWeight)
    {
        boolean result = false;

        if (!this.equals(endVertex))
        {
            Iterator<VertexInterface<T>> neighbors = this.getNeighborIterator();
            boolean duplicateEdge = false;

            while (!duplicateEdge && neighbors.hasNext())
            {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor))
                    duplicateEdge = true;
            }

            if (!duplicateEdge)
            {
                edgeList.add(new Edge(endVertex, edgeWeight));
                result = true;
            }
        }
        return result;
    }
    
    //method:  connect
    //purpose: This method calls the connect method with endVertex and 0 as the
    //         parameters. It returns the return of the connect method call.
    public boolean connect(VertexInterface<T> endVertex)
    {
        return connect(endVertex, 0);
    }
    
    //method:  getNeighborIterator
    //purpose: This method returns a new NeighborIterator.
    public Iterator<VertexInterface<T>> getNeighborIterator()
    {
        return new NeighborIterator();
    }
    
    //method:  getWeightIterator
    //purpose: This method creates new list of doubles. It then iterates through
    //         edgeList and gets the weight of each Edge and adds it to the new
    //         list. It then returns the new list.
    public Iterator<Double> getWeightIterator()
    {
        ListWithIteratorInterface<Double> weights = new LinkedListWithIterator<Double>();
        Iterator<Edge> edges = edgeList.iterator();
        
        while(edges.hasNext())
        {
            Edge edge = edges.next();
            weights.add(edge.getWeight());
        }
        
        return weights.getIterator();
    }
    
    public class NeighborIterator implements Iterator <VertexInterface<T>>
    {
        private Iterator<Edge> edges;
        
        private NeighborIterator()
        {
            edges = edgeList.iterator();
        }
        
        //method:  hasNext
        //purpose: This method returns true if the edges field hasNext.
        public boolean hasNext()
        {
            return edges.hasNext();
        }
        
        //method:  next
        //purpose: This method returns the next Edge if there is any.
        public VertexInterface<T> next()
        {
            VertexInterface<T> nextNeighbor = null;
            if(edges.hasNext())
            {
                Edge edgeToNextNeighbor = edges.next();
                nextNeighbor = edgeToNextNeighbor.getEndVertex();
            }
            else
                throw new NoSuchElementException();
            
            return nextNeighbor;
        }
        
        public void remove()
        {
            throw new UnsupportedOperationException("remove() is not " +
                                                    "supported by this iterator.");
        }
        
        //method:  remove
        //purpose: This method removes the key from the edgeList.
        public void remove(T key)
        {
            edgeList.remove(key);
        }
    }
    
    //method:  getUnvisitedNeighbor
    //purpose: This method iterates through the neighbors of the vertex and 
    //         returns the first neighbor that is not visited.
    public VertexInterface<T> getUnvisitedNeighbor()
    {
        VertexInterface<T> result = null;
        
        Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
        while(neighbors.hasNext() && (result == null))
        {
            VertexInterface<T> nextNeighbor = neighbors.next();
            if(!nextNeighbor.isVisited())
                result = nextNeighbor;
        }
        
        return result;
    }
    
    //method:  hasUnvisitedNeighbor
    //purpose: This method iterates through the neighbors of the vertex and
    //         returns true if an unvisited neighbor is found and false if not.
    public boolean hasUnvisitedNeighbor()
    {
        boolean result = false;
        
        Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
        while(neighbors.hasNext() && !result)
        {
            VertexInterface<T> nextNeighbor = neighbors.next();
            if(!nextNeighbor.isVisited())
                result = true;
        }
        
        return result;
    }
    
    //method:  hasNeighbor
    //purpose: This method returns true if edgeList is not empty and false if it
    //         is empty.
    public boolean hasNeighbor()
    {
        return !edgeList.isEmpty();
    }
    
    //method:  equals
    //purpose: This method compares the label of two vertices and returns true 
    //         if the labels are the same.
    public boolean equals(Object other)
    {
        boolean result;
        
        if((other == null) || (getClass() != other.getClass()))
            result = false;
        else
        {
            Vertex<T> otherVertex = (Vertex<T>)other;
            result = label.equals(otherVertex.label);
        }
        
        return result;
    }
}
