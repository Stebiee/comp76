import java.rmi.UnexpectedException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;


/**
 * Assignment for the Implementing Lists module.
 * You need to implement all the TODOs.
 * To see what each method needs to do check out: 
 * https://docs.oracle.com/javase/8/docs/api/java/util/List.html
 * @author Esteban Madrigal
 */
public class MyDoubleLinkedList<T> implements List<T> {
    /**
     * Each node in the linked list is represented by this class. It contains a forward and backward pointer
     * to allow traversal in both directions
     */
    static class Node<T> {
        T data;
        Node<T> prev, next;

        /**
         * Creates a new node and set its prev/next pointers. 
         * NOTE: This does NOT adjust the pointers of the prev and next nodes by design.
         */
        public Node(T t, Node<T> prev, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.data = t;
        }
    };

    /**
     * The head of the list.
     */ 
    Node<T> head;
    /**
     * The tail of the list.
     */
    Node<T> tail;

    /**
     * The number of elements in the list.
     */
     int numElements;

    /**
     * The list iterator implementation for the double linked list.
     * NOTE: You need to implement all the TODOs below.
     * To understand what each method has to do check out:
     * https://docs.oracle.com/javase/8/docs/api/java/util/ListIterator.html
     */
    static class DoubleLinkedListIterator<T> implements ListIterator<T> {
        Node<T> current;

        public DoubleLinkedListIterator(Node<T> head) {
            this.current = head;
        }
        /**
         * @return true if Node has element in forward position
         * @return false if Node has no element in forward position
         */
        @Override
        public boolean hasNext() {
            // pointer to next should be null if empty
            // return true if not null
            return current.next != null && current.data != null;
        }

        /*
         * @return the next element in the list and advances the cursor position
         */
        @Override
        public T next() {
            if (!hasNext()) {
                // there is no element to the right
                throw new NoSuchElementException(); 
            }
            // there is an element to the right
            // return next element in list and advance cursor
            T nextElement = current.next.data;
            // advance cursor to the right
            current = current.next;
            return nextElement;
        }

        /**
         * @return true if Node has element in backward position
         * @return false if Node has no element in backward position
         */
        @Override
        public boolean hasPrevious() {
            // prev should be null if empty
            // if not null contains element
            return current.prev != null && current != null;
        }

        @Override
        public T previous() {
            if (!hasPrevious()) {
                // there is no element to the left
                throw new NoSuchElementException(); 
            }
            // there is an element to the left
            T prevElement = current.prev.data; // return next element
            current = current.prev; // advance cursor to the left
            return prevElement;
        }

        @Override
        public int nextIndex() {
            // No need to implement this method.
            throw new UnsupportedOperationException("Unimplemented method 'nextIndex'");
        }

        @Override
        public int previousIndex() {
            // No need to implement this method.
            throw new UnsupportedOperationException("Unimplemented method 'previousIndex'");
        }

        /**
         * Removes from the list the last element that was returned by next() or previous() 
         */
        @Override
        public void remove() {
            if (current == null) {
                throw new IllegalStateException("No element to remove");
            }
        
            Node<T> remove = current.prev; // Node to be removed
            Node<T> prevNode = remove.prev; // node before remove
            Node<T> nextNode = remove.next; // node after remove
        
            if (prevNode != null) {
                // there is an element before current node
                prevNode.next = nextNode; // point previous to node after current
            }
        
            if (nextNode != null) {
                // there is an element after current node
                nextNode.prev = prevNode; // point node after current to previous
            }
        
            if (remove == current && remove.prev == null) {
                // node to remove is head
                current = nextNode; // update head
            }
        
            // node to remove has no nodes pointing to it
            // make all pointers null
            remove.prev = null;
            remove.next = null;
            remove.data = null;
        }

        @Override
        public void set(T e) {
            // No need to implement this method.
            throw new UnsupportedOperationException("Unimplemented method 'set'");
        }

        @Override
        public void add(T e) {
            // No need to implement this method.
            throw new UnsupportedOperationException("Unimplemented method 'add'");
        }
    }

    /**
     * @return int amount of nodes
     */
    @Override
    public int size() {
        return numElements;
    }

    /**
     * @return true if linked list has 0 elements
     * @return false if linked list contains elements
     */
    @Override
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * @return true if o is in list
     */
    @Override
    public boolean contains(Object o) {
        // iter through linked list starting at head 
        // when an element is found return true or false
        Node<T> pointer = head;
        
        // loop through all nodes 
        while (pointer != null) {
            if (pointer.equals(o)) {
                return true;
            }

            pointer = pointer.next;
        }

        // looped through all elements none equal found
        return false;
    }

    /**
     * @return an iterator over the elements in this list in proper sequence
     */
    @Override
    public Iterator<T> iterator() {
        return new DoubleLinkedListIterator<T>(head);
    }

    /**
     * @return an array containing all of the elements in this list in proper sequence (from first to last element)
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[numElements]; // declare an array with element size equal to linkedList
        
        if (head.data == null) return array;

        Node<T> pointer = head; // node used to go through linked list
        int i = 0; // stores index of array

        while (pointer != null && i < numElements) {
            if (pointer.data == null && i >= array.length) break;
            array[i] = pointer.data; // assign Node data to current index of array
            pointer = pointer.next; // go to next node
            i++;
        }

        // end of linked list was reached
        return array;
    }

    @Override
    public <E> E[] toArray(E[] a) {
        // No need to implement this method.
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

    /**
     * Appends the specified element to the end of this list
     * @return true
     */
    @Override
    public boolean add(T t) {
        // creating a new node to insert
        Node<T> node = new Node<>(t, null, null);

        if (tail == null) {
            // list is empty
            head = tail = node; // set the head and tail to new node 
            numElements = 1; // list is 1 element in size
        } else {
            // linkedList is one element in size or more
            node.prev = tail; // point node to old tail
            tail.next = node; // point old tail to point at node
            tail = node; // set node to be new tail
            numElements++; // increment element count
        }

        return true;
    }

    /**
     * Removes the first occurrence of the specified element from this list, if it is present
     * @return true if an element is removed
     */
    @Override
    public boolean remove(Object o) {
        Node<T> pointer = head;
        
        // loop through all nodes 
        while (pointer != null) {
            if (pointer.data.equals(o)) {
                // o was found
                if (pointer.prev == null) {
                    // o is head
                    head = pointer.next; // update head
                }
                
                pointer.prev.next = pointer.next; // update prev.next to be after o
                pointer.next.prev = pointer.prev; // update next.prev to be before o

                numElements--;
                return true; // element was removed
            }

            pointer = pointer.next; // go through increment through linked list
        }

        return false;
    }

    /**
     * @return true if this list contains all of the elements of the specified collection
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                // an element was not found
                return false; 
            }
        }
        // all elements in c are in linked list
        return true;
    }

    /**
     * Appends all of the elements in the specified collection to the end of this list
     * @return true if elements are added
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean isModified = false; // store whether elements are added
        for (T element : c) {
            if (add(element)) {
                isModified = true;
                
            }
        }
        return isModified;
    }

    /**
     * Inserts all of the elements in the specified collection into this list at the specified position
     * @return true if elements are added
     */
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        for (T elements : c) {
            add(index, elements);
        }
        return true;
    }

    /**
     * Removes from this list all of its elements that are contained in the specified collection
     * @return true if elements are removed
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isModified = false; // store whether elements are added

        for (Object element : c) {
            if (remove(element)) {
                // an element was removed
                isModified = true;
            }
        }

        return isModified;
    }

    /**
     * Retains only the elements in this list that are contained in the specified collection
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isModified = false; // store whether the list is modified

        Iterator<T> iter = iterator();

        while (iter.hasNext()) {
            // iter has a next element 
            T element = iter.next(); // set element to next iter

            if (!c.contains(element)) {
                // element is not in collection
                iter.remove();
                isModified = true;
            }
        }

        return isModified;
    }

    /**
     * Removes all of the elements from this list
     */
    @Override
    public void clear() {
        // set head and tail to null
        head = tail = null;
        numElements = 0;
    }

    /**
     * throws IndexOutOfBoundsException - if the index is out of range
     * @return the element at the specified position in this list
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= numElements && index != 0) {
            // index is out of bounds
            throw new IndexOutOfBoundsException();
        } 
        Node<T> node = head;
        
        // loop until having reached element at index
        for (int i = 0; i <= index; i++) {
            if (index == i) {
                // arrived at i element
                return node.data;
            }
            node = node.next;
        }

        // having iter until index return that elements data
        return node.data;
    }

    /**
     * Replaces the element at the specified position in this list with the specified element
     * @return element previously at index
     */
    @Override
    public T set(int index, T element) {
        if (index < 0 || index >= numElements) {
            // index is out of bounds
            throw new IndexOutOfBoundsException();
        }

        Node<T> pointer = head;
        for (int i = 0; i < index; i ++) {
            pointer = pointer.next;
        }
        // arrived at the given index
        T prevData = pointer.data;
        pointer.data = element;

        return prevData;
    }

    /**
     * Inserts the specified element at the specified position in this list 
     */
    @Override
    public void add(int index, T element) {
        if (index < 0 || index >= numElements) {
            // index is out of bounds
            throw new IndexOutOfBoundsException();
        } 

        Node<T> node = new Node<>(element, null, null);

        if (head == null) {
            head = tail = node; // list was empty
        } else if (index == 0) {
            // insert node before head
            node.next = head; // point node to head
            head.prev = node; // point old head prev to node
            head = node; // set node to head
        } else if (index == numElements) {
            // insert node after tail
            node.prev = tail; // point node prev to tail
            tail.next = node; // point old next tail to node
            tail = node; // set node to tail
        } else {
            // somewhere inside list
            Node<T> pointer = head;

            for (int i = 0; i < index; i++) {
                pointer = pointer.next;
            }
            // arrived at node before index
            node.next = pointer.next; // point node to element at index
            pointer.next = node; // element before index points to node
            node.prev = pointer; // node prev set to element before index
            node.next.prev = node; // set element at index prev to node
        }

        numElements++; // node should be at index
    }

    /**
     * Removes the element at the specified position in this list
     * @return element previously at index
     */
    @Override
    public T remove(int index) {
        T prevData; // data previously at index
        if (index < 0 || index >= numElements) {
            // index is out of bounds
            throw new IndexOutOfBoundsException();
        } else if (index == 0) {
            // index zero is head
            prevData = head.data; // store previous data before overwriting
            remove(head);
            return prevData;
        } else if (index == numElements - 1) {
            // index is at tail of list
            prevData = tail.data; // store previous data before overwriting
            remove(tail);
            return prevData;
        }
        // index somewhere in middle of list
        // go to node at index
        Node<T> node = head.next;
        int i = 1; // since node starts after head
        while (i != index) {
            node = node.next;
        }
        // arrived at node at index
        prevData = node.data; // store previous data before overwriting
        remove(node);
        return prevData;
    }

    /**
     * 
     * @return index of the first occurrence of the specified element in this list from the left
     */
    @Override
    public int indexOf(Object o) {
        Node<T> pointer = head;
        int i = 0;

        // loop until having reached the element
        while (pointer != null) {
            if (pointer.equals(o)) {
                return i;
            }
            pointer = pointer.next;
            i++; // increment index
        }
        // looped through linked list without finding o
        return -1;
    }

    /**
     * returns -1 if object not in list
     * @return the index of the last occurrence of the specified element in this list
     */
    @Override
    public int lastIndexOf(Object o) {
        Node<T> pointer = tail;
        int i = numElements - 1; // index of tail

        // loop until having reached the element starting from the tail
        while (pointer != null) {
            if (pointer.equals(o)) {
                return i;
            }
            pointer = pointer.prev;
            i--; // increment index
        }
        // looped through linked list without finding o
        return -1;
    }

    /**
     * 
     * @return a list iterator over the elements in this list
     */
    @Override
    public ListIterator<T> listIterator() {
        return new DoubleLinkedListIterator<T>(head);
    }

    /**
     * @param index of first element to return
     * @return a list iterator over the elements in this list (in proper sequence), starting at the specified position in the list.
     */
    @Override
    public ListIterator<T> listIterator(int index) {
        if (index < 0 || index >= numElements) {
            // index is out of bounds
            throw new IndexOutOfBoundsException();
        }

        Node<T> pointer = head;
        for (int i = 0; i < index; i++) {
            pointer = pointer.next;
        }

        return new DoubleLinkedListIterator<>(pointer);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        // No need to implement this method.
        throw new UnsupportedOperationException("Unimplemented method 'subList'");
    }
}
