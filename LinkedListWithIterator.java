/*************************************************************** 
*   file: LinkedListWithIterator.java 
*   author: J. Arellano 
*   class: CS 241 – Data Structures and Algorithms II
* 
*   assignment: program 3
*   date last modified: 3/8/2018 
* 
*   purpose: This class creates a class that can create a list with iterators.
*            This class implements all the methods in the ListWithIterator
*            interface.
* 
****************************************************************/
package directedgraph;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListWithIterator<T> implements ListWithIteratorInterface<T>
{
    private Node firstNode;
    private int numberOfEntries;
    
    public LinkedListWithIterator()
    {
        initializeDataFields();
    }
    
    //method:  iterator
    //purpose: This method returns a new IteratorForLinkedList.
    public Iterator<T> iterator()
    {
        return new IteratorForLinkedList();
    }
    
    //method:  getLength
    //purpose: This method returns the field numberOfEntries.
    public int getLength()
    {
        return numberOfEntries;
    }
    
    //method:  getCityCode
    //purpose: This method returns a new IteratorForLinkedList.
    public Iterator<T> getIterator()
    {
        return iterator();
    }
    
    //method:  clear
    //purpose: This method calls the initializeDataFields method.
    public void clear()
    {
        initializeDataFields();
    }
    
    //method:  getCityCode
    //purpose: This method sets firstNode to null and numberOfEntries to 0.
    private void initializeDataFields()
    {
        firstNode = null;
        numberOfEntries = 0;
    }
    
    //method:  add
    //purpose: This method creates a new Node and sets it to the firstNode if
    //         the linked list is empty. If it is not empty, it finds the last
    //         node's next node to the new node. Finally, it adds one to the
    //         numberOfEntries.
    public void add(T newEntry)
    {
        Node newNode = new Node(newEntry);
        if(isEmpty())
            firstNode = newNode;
        else
        {
            Node lastNode = getNodeAt(numberOfEntries);
            lastNode.setNextNode(newNode);
        }
        numberOfEntries++;
    }
    
    //method:  add
    //purpose: This method adds a new Node to a certain position.
    public void add(int newPosition, T newEntry)
    {
        if((newPosition >= 1) && (newPosition <= numberOfEntries + 1))
        {
            Node newNode = new Node(newEntry);
            if(newPosition == 1)
            {
                newNode.setNextNode(firstNode);
                firstNode = newNode;
            }
            else
            {
                Node nodeBefore = getNodeAt(newPosition - 1);
                Node nodeAfter = nodeBefore.getNextNode();
                newNode.setNextNode(nodeAfter);
                nodeBefore.setNextNode(newNode);
            }
            numberOfEntries++;
        }
        else
            throw new IndexOutOfBoundsException(
                        "Illegal position given to add operation.");
    }
    
    //method:  getNodeAt
    //purpose: This method verifies that the List is not empty, the givenPosition
    //         is not less than 1 and greater than the total numberOfEntries.
    //         it then iterates through the list until the counter reaches the
    //         given position. It finally returns the currentNode.
    private Node getNodeAt(int givenPosition)
    {
        assert (firstNode != null) &&
               (1 <= givenPosition) && (givenPosition <= numberOfEntries);
        Node currentNode = firstNode;
        for(int counter = 1; counter < givenPosition; counter++)
            currentNode = currentNode.getNextNode();
        assert currentNode != null;
        
        return currentNode;
    }
    
    //method:  remove
    //purpose: This method removes a node at a given position if the
    //         givenPosition is not less than 1 and not greater than the
    //         numberOfEntries. It finds the node before the given position and
    //         sets the nextNode to the nextNode of the givenPosition node.
    public T remove(int givenPosition)
    {
        T result = null;
        if((givenPosition >= 1) && (givenPosition <= numberOfEntries))
        {
            assert !isEmpty();
            if(givenPosition == 1)
            {
                result = firstNode.getData();
                firstNode = firstNode.getNextNode();
            }
            else
            {
                Node nodeBefore = getNodeAt(givenPosition - 1);
                Node nodeToRemove = nodeBefore.getNextNode();
                result = nodeToRemove.getData();
                Node nodeAfter = nodeToRemove.getNextNode();
                nodeBefore.setNextNode(nodeAfter);
            }
            numberOfEntries--;
            return result;
        }
        else
            throw new IndexOutOfBoundsException(
                      "Illegal position given to remove operation.");
    }
    
    //method:  replace
    //purpose: This method finds the given positionNode and changes the data
    //         to newEntry and returns the originalEntry.
    public T replace(int givenPosition, T newEntry)
    {
        if((givenPosition >= 1) && (givenPosition <= numberOfEntries))
        {
            assert !isEmpty();
            Node desiredNode = getNodeAt(givenPosition);
            T originalEntry = desiredNode.getData();
            desiredNode.setData(newEntry);
            return originalEntry;
        }
        else
            throw new IndexOutOfBoundsException(
                      "Illegal position given to replace operation.");
    }
    
    //method:  getEntry
    //purpose: This method gets the data of the node at the given position if 
    //         the node exists.
    public T getEntry(int givenPosition)
    {
        if((givenPosition >= 1) && (givenPosition <= numberOfEntries))
        {
            assert !isEmpty();
            return getNodeAt(givenPosition).getData();
        }
        else
            throw new IndexOutOfBoundsException(
                      "Illegal position given to getEntry operation.");
    }
    
    //method:  contains
    //purpose: This method iterates through the nodes until an entry is found in
    //         one of the nodes or there are no more nodes. If the entry is 
    //         found the method returns true. If not, the method returns false.
    public boolean contains(T anEntry)
    {
        boolean found = false;
        Node currentNode = firstNode;
        
        while(!found && (currentNode != null))
        {
            if(anEntry.equals(currentNode.getData()))
                    found = true;
            else
                currentNode = currentNode.getNextNode();
        }
        return found;
    }
    
    //method:  isEmpty
    //purpose: This method return false if the numberOfEntries is 0 and false if
    //         if is not.
    public boolean isEmpty()
    {
        boolean result;
        
        if(numberOfEntries == 0)
        {
            assert firstNode == null;
            result = true;
        }
        else
        {
            assert firstNode != null;
            result = false;
        }
        return result;
    }
    
    //method:  toArray
    //purpose: This method creates a new generic array of size numberOfEntries.
    //         The method then populates the array by iterating through all the
    //         nodes and getting the data of each node. The method finally 
    //         returns the array.
    public T[] toArray()
    {
        @SuppressWarnings("unchecked")
        T[] result = (T[]) new Object[numberOfEntries];
        
        int index = 0;
        Node currentNode = firstNode;
        while((index < numberOfEntries) && (currentNode != null))
        {
            result[index] = currentNode.getData();
            currentNode = currentNode.getNextNode();
            index++;
        }
        return result;
    }
    
    private class IteratorForLinkedList implements Iterator
    {
        private Node nextNode;
        private IteratorForLinkedList()
        {
            nextNode = firstNode;
        }
        
        //method:  next
        //purpose: This method returns sets the iteratorForLinkedList to the
        //         nextNode and returns the old node's data.
        public T next()
        {
            if(hasNext())
            {
                Node returnNode = nextNode;
                nextNode = nextNode.getNextNode();
                
                return returnNode.getData();
            }
            else
                throw new NoSuchElementException("Illegal call to next();" 
                                                 + "iterator is after end "
                                                 + "of list.");
        }
        
        //method:  hasNext
        //purpose: This method returns true if the nextNode field is not null 
        //         and false if it is null.
        public boolean hasNext()
        {
            return nextNode != null;
        }
        
        //method:  remove
        //purpose: This method makes the inherited remove method unsupported.
        public void remove()
        {
            throw new UnsupportedOperationException("remove() is not " +
                                                    "supported by this iterator.");
        }
    }
    
    private class Node
    {
        private T data;
        private Node next;
        
        private Node(T dataPortion)
        {
            this(dataPortion, null);
        }
        
        private Node(T dataPortion, Node nextNode)
        {
            data = dataPortion;
            next = nextNode;
        }
        
        //method:  getData
        //purpose: This method returns the data field.
        public T getData()
        {
            return data;
        }
        
        //method:  setData
        //purpose: This method sets the data field to the given data.
        public void setData(T data)
        {
            this.data = data;
        }
        
        //method:  getNextNode
        //purpose: This method returns the next field.
        public Node getNextNode()
        {
            return next;
        }
        
        //method:  setNextNode
        //purpose: This method sets next to the given Node.
        public void setNextNode(Node next)
        {
            this.next = next;
        }
    }
}
