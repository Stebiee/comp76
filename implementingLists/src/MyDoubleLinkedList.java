import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;


/**
 * Assignment for the Implementing Lists module.
 * You need to implement all the TODOs.
 * To see what each method needs to do check out: 
 * https://docs.oracle.com/javase/8/docs/api/java/util/List.html
 * @author Esteban MadrigalD
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

        @Override
        public void remove() {
            // No need to implement this method.
            throw new UnsupportedOperationException("Unimplemented method 'remove'");
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
        return numElements == 0;
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
        // creating a new node where prev is old tail, next is null
        Node<T> node = new Node<>(t, tail, null);

        if (head == null && tail == null) {
            // list is empty
            head = tail = node; // set the head to new node 
        } else {
            // set old tail forward pointer to new node
            tail.next = node;
            tail = node; // new node is set to tail
            numElements++; // since element added increment
        }

        return true;
    }

    /**
     * Removes the first occurrence of the specified element from this list, if it is present
     * @return true if an element is removed
     */
    @Override
    public boolean remove(Object o) {
        Node<T> prev = null; // reference to node before o
        Node<T> pointer = head; // begin at head of the list
        Node<T> after = head.next; //  reference to node after o
        if (pointer.data == null) return false;
        System.out.print(prev);
        System.out.print(pointer);
        System.out.println(after);

        while (pointer != null) {
            if (pointer.data.equals(o)) {
                // arrived at element to be removed
                if (pointer.prev == null) {
                    System.out.print(prev);
                    System.out.print(pointer);
                    System.out.println(after);
                    // o is the head
                    head = pointer.next; // set new head to element after it
                    head.prev = null; // remove old reference to origional head
                    numElements--; // decrement amount of elements
                    return true;
                } 
                if (pointer.next == null) {
                    System.out.print(prev);
                    System.out.print(pointer);
                    System.out.println(after);
                    // o is tail 
                    tail = pointer.prev; // set new tail to element before it
                    tail.next = null; // remove old reference to origional tail
                    numElements--; // decrement amount of
                    return true;
                }
                System.out.print(prev);
                System.out.print(pointer);
                System.out.println(after);

                // o is somewhere in middle of list
                prev.next = after; // elem before o .next to elem after o 
                after.prev = prev; // elem after o .prev to elem before o

                // o should have been removed from any reference
                numElements--;
                return true;
            }
        }
        // pointer is null, list is empty or list does not contain o
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
                // an element was added
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
        // TODO: Implement this method for extra credit.
        throw new UnsupportedOperationException("Unimplemented method 'addAll'");
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
        // set 
        head.next = null;
        head.data = null;
        head.prev = null;
        tail.prev = null;
        tail.data = null;
        tail.next = null;
        numElements = 0;
    }

    /**
     * throws IndexOutOfBoundsException - if the index is out of range
     * @return the element at the specified position in this list
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= numElements) {
            // index is out of bounds
            throw new IndexOutOfBoundsException();
        } 
        
        if (index == 0 && head.data != null) {
            return head.data; // index 0 is first element
        } else if (index == numElements - 1 && tail.data != null) {
            return tail.data; // index is at end of linked list
        }

        Node<T> node = head;
        
        // index isnt 0 or at end of the list
        for (int i = 0; i < index; i++) {
            node = node.next;
        }

        System.out.println("stopped at " + index);
        System.out.println("data is " + node.data);
        // having iter until index return that elements data
        return node.data;
    }

    /**
     * Replaces the element at the specified position in this list with the specified element
     * @return element previously at index
     */
    @Override
    public T set(int index, Object element) {
        if (index < 0 || index >= numElements) {
            // index is out of bounds
            throw new IndexOutOfBoundsException();
        }
        
        Node<T> node = head;

        for (int i = 0; i <= index; i++) {
            node = node.next;
        }

        T prevData = node.data;
        node.data = (T) element;
        return prevData;
        
    }

    /**
     * Inserts the specified element at the specified position in this list 
     */
    @Override
    public void add(int index, T element) {
        if (index < 0 || index <= numElements) {
            // index is out of bounds
            throw new IndexOutOfBoundsException();
        } else if (index == 0) {
            // index zero is head
            head.data = element;
        } else if (index == numElements - 1) {
            // index is at tail of list
            tail.data = element;
        }
        // index somewhere in middle of list
        // go to node at index
        Node<T> node = head.next;
        int i = 1; // since node starts after head
        while (i != index) {
            node = node.next;
        }
        // arrived at node at index
        node.data = element;
    }

    /**
     * Removes the element at the specified position in this list
     * @return element previously at index
     */
    @Override
    public T remove(int index) {
        T prevData; // data previously at index
        if (index < 0 || index <= numElements) {
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

    @Override
    public ListIterator<T> listIterator(int index) {
        // TODO: You can implement this method for extra credit.
        throw new UnsupportedOperationException("Unimplemented method 'listIterator'");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        // No need to implement this method.
        throw new UnsupportedOperationException("Unimplemented method 'subList'");
    }
}
