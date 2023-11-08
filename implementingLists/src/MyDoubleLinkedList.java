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

        /**
         * @return true if Node has element in forward position
         * @return false if Node has no element in forward position
         */
        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        @Override
        public T next() {
            // TODO: Implement this method.
            throw new UnsupportedOperationException("Unimplemented method 'next'");
        }

        /**
         * @return true if Node has element in backward position
         * @return false if Node has no element in backward position
         */
        @Override
        public boolean hasPrevious() {
            return current != null;
        }

        @Override
        public T previous() {
            // TODO: Implement this method.
            throw new UnsupportedOperationException("Unimplemented method 'previous'");
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
     * @return true if o is in 
     */
    @Override
    public boolean contains(Object o) {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("Unimplemented method 'contains'");
    }

    @Override
    public Iterator<T> iterator() {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }

    @Override
    public Object[] toArray() {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

    @Override
    public <E> E[] toArray(E[] a) {
        // No need to implement this method.
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

    @Override
    public boolean add(T t) {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public boolean remove(Object o) {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("Unimplemented method 'containsAll'");
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("Unimplemented method 'addAll'");
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        // TODO: Implement this method for extra credit.
        throw new UnsupportedOperationException("Unimplemented method 'addAll'");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // TODO: Implement this method for extra credit.
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO: Implement this method for extra credit.
        throw new UnsupportedOperationException("Unimplemented method 'retainAll'");
    }

    @Override
    public void clear() {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }

    @Override
    public T get(int index) {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public T set(int index, Object element) {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("Unimplemented method 'set'");
    }

    @Override
    public void add(int index, T element) {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public T remove(int index) {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public int indexOf(Object o) {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("Unimplemented method 'indexOf'");
    }

    @Override
    public int lastIndexOf(Object o) {
        // TODO: Implement this method for extra credit.
        throw new UnsupportedOperationException("Unimplemented method 'lastIndexOf'");
    }

    @Override
    public ListIterator<T> listIterator() {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("Unimplemented method 'listIterator'");
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
