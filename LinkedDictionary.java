/*************************************************************** 
*   file: LinkedDictionary.java 
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
import java.util.NoSuchElementException;

public class LinkedDictionary<K , V>
        implements DictionaryInterface<K,V>
{
    private Node firstNode;
    private int numberOfEntries;
    
    public LinkedDictionary()
    {
        initializeDataFields();
    }
    
    //method:  initializedDataFields
    //purpose: This method sets firstNode to null and numberOfEntries to 0.
    private void initializeDataFields()
    {
        firstNode = null;
        numberOfEntries = 0;
    }
    
    //method:  getValue
    //purpose: This method iterates through the nodes to find a specific node
    //         by the key. Once the node is found the value is returned. If the
    //         node with the key is not found then null is returned.
    public V getValue(K key)
    {
        V result = null;
        if(firstNode != null)
        {
            Iterator<K> keys = getKeyIterator();
            Node currentNode = firstNode;
            while(keys.hasNext() && !currentNode.getKey().equals(key))
            {
                currentNode = currentNode.getNextNode();
                keys.next();
            }
            if(currentNode != null)
            {
                result = currentNode.getValue();
            }
        }
        return result;
    }
    
    //method:  getSize
    //purpose: This method returns numberOfEntries.
    public int getSize()
    {
        return numberOfEntries;
    }
    
    //method:  clear
    //purpose: This method calls initializeDataFields method.
    public void clear()
    {
        initializeDataFields();
    }
    
    //method:  isEmpty
    //purpose: This method return true if the numberOfEntries is 0 and false if
    //         it is not.
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
    
    //method:  add
    //purpose: This method iterates through the nodes until there are none left
    //         of the given key is found. If the key is found, then the value
    //         of the node is updated to the new value. If the key was not found
    //         a new Node is created and attached to the final node. It finally
    //         adds one to the numberOfEntries.
    public V add(K key, V value)
    {
        V result = null;
        
        Node currentNode = firstNode;
        Node nodeBefore = null;
        while((currentNode != null) && !key.equals(currentNode.getKey()))
        {
            nodeBefore = currentNode;
            currentNode = currentNode.getNextNode();
        }
        
        if((currentNode != null) && key.equals(currentNode.getKey()))
        {
            result = currentNode.getValue();
            currentNode.setValue(value);
        }
        else
        {
            Node newNode = new Node(key, value);
            if(nodeBefore == null)
            {
                newNode.setNextNode(firstNode);
                firstNode = newNode;
            }
            else
            {
                newNode.setNextNode(currentNode);
                nodeBefore.setNextNode(newNode);
            }
            numberOfEntries++;
        }
        return result;
    }
    
    //method:  getKeyIterator
    //purpose: This method returns a new KeyIterator.
    public Iterator<K> getKeyIterator()
    {
        return new KeyIterator();
    }
    
    //method:  getValueIterator
    //purpose: This method returns a new ValueIterator.
    public Iterator<V> getValueIterator()
    {
        return new ValueIterator();
    }
    
    //method:  contains
    //purpose: This method iterators through the nodes and returns true if any 
    //         of the nodes contains the key and false if the key is not found.
    public boolean contains(K key)
    {
        boolean found = false;
        Node currentNode = firstNode;
        
        while(!found && (currentNode != null))
        {
            if(key.equals(currentNode.getKey()))
                    found = true;
            else
                currentNode = currentNode.getNextNode();
        }
        return found;
    }
    
    private class ValueIterator  implements Iterator<V>
    {
        private Node nextNode;
        
        private ValueIterator()
        {
            nextNode = firstNode;
        }
        
        //method:  hasNext
        //purpose: This method returns true if the nextNode field is not null
        //         and false if it is.
        public boolean hasNext()
        {
            return nextNode != null;
        }
        
        //method:  next
        //purpose: This method returns the nextNode's value if it has next. It
        //         also sets the nextNode to the nextNode's nextNode, and 
        //         returns the nextNode's value. It throws an exception if
        //         nextNode is null.
        public V next()
        {
            V result;
            if(hasNext())
            {
                result = nextNode.getValue();
                nextNode = nextNode.getNextNode();
            }
            else
                throw new NoSuchElementException();
            return result;
        }
        
        //method:  remove
        //purpose: This method throws a new exception.
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
    
    private class KeyIterator  implements Iterator<K>
    {
        private Node nextNode;
        
        private KeyIterator()
        {
            nextNode = firstNode;
        }
        
        //method:  hasNext
        //purpose: This method returns true if the nextNode field is not null
        //         and false if it is.
        public boolean hasNext()
        {
            return nextNode != null;
        }
        
        //method:  next
        //purpose: This method returns the nextNode's key if it has next. It
        //         also sets the nextNode to the nextNode's nextNode, and 
        //         returns the nextNode's key. It throws an exception if
        //         nextNode is null.
        public K next()
        {
            K result;
            if(hasNext())
            {
                result = nextNode.getKey();
                nextNode = nextNode.getNextNode();
            }
            else
                throw new NoSuchElementException();
            return result;
        }
        
        //method:  remove
        //purpose: This method throws a new exception.
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
    
    private class Node
    {
        private K key;
        private V value;
        private Node next;
        
        private Node(K key)
        {
            this(key, null);
        }
        
        private Node(K key, V value)
        {
            this(key, value, null);
        }
        
        private Node(K key, V value, Node next)
        {
            this.key = key;
            this.value = value;
            this.next = next;
        }
        
        //method:  getValue
        //purpose: This method returns the value field.
        public V getValue()
        {
            return value;
        }
        
        //method:  setValue
        //purpose: This method sets the value field to the input.
        public void setValue(V value)
        {
            this.value = value;
        }
        
        //method:  getKey
        //purpose: This method returns the key field to the input.
        public K getKey()
        {
            return key;
        }
        
        //method:  setKey
        //purpose: This method sets the key field to the input.
        public void setKey(K key)
        {
            this.key = key;
        }
        
        //method:  getNextNode
        //purpose: This method returns the next field.
        public Node getNextNode()
        {
            return next;
        }
        
        //method:  setNextNode
        //purpose: This method sets the next field to the input.
        public void setNextNode(Node next)
        {
            this.next = next;
        }
    }
}
