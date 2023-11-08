import java.util.NoSuchElementException;

/**
 * toString() print object easily
 * size() size of linked list
 * clear() remove all elements
 * isEmpty() check if linked list is empty
 *
 * addFirst(e) add element e to head position
 * addLast(e) add element e to tail position
 * add(index, e) add element e to index position
 *
 * removeFirst() remove head element
 * removeLast() remove tail element
 * remove(index) remove element and index
 *
 * getFirst() retrieve head element
 * getLast() retrieve tail element
 * get(index) retrieve element at index
 * set(index, e) change element at index with e
 *
 * contains(e) is e in linked list
 * indexOf(e) index of e in linked list (left most)
 * lastIndexOf(e) index of e in linked list (right most)
 *
 * iterator() an enhanced for each loop
 * 
 * linked lists are a data structure where nodes are connected by links
 * the head points to the node to its right etc
 * @author Esteban Madrigal
 */
public class MyLinkedList<E> {

    // reference to head Node
    private Node<E> head;
    // reference to tail node
    private Node<E> tail;

    // data field for size of linked list
    private int size;

    /**
     * on empty linked list creation assign head and tail values null
     * since linked list is empty size is also 0
     */
    public MyLinkedList() {
        this.head = this.tail = null;
        this.size = 0;
    }

    @Override
    public String toString() {
        if (head == null && tail == null) {
            // linked list is empty
            return "[]";
        } else if (head == tail){
            // there is one element in linked list
            return "[" + head.toString() + "]";
        } else {
            // there is more than one element
            // create a pointer for the first element
            Node<E> pointer = head;

            // what is going to be the final result
            String result = "[";

            // create a loop to move through linked list
            // stopping when pointer has no value
            while (pointer != null) {
                result += pointer.toString() + ", ";
                pointer = pointer.next;
            }

            // ignore the last 2 character since they will be ", "
            return result.substring(0, result.length() - 2) + "]";
        }
    }
    
    /**
     * setting head and tail to null removes any access to nodes in between
     * the java garbage collector will remove the nodes from memory
     */
    public void clear() {
        head = tail = null;
        size = 0;
    }

    /**
     * @return true if size == 0
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @param data adds to head position
     */
    public void addFirst(E data) {
        // if there are no nodes, node to add is first
        if (head == null) {
            // for first element head and tail point to data
            head = tail = new Node<>(data);
        } else {
            // there is one node or more in linked list
            // create a new node
            Node<E> newNode = new Node<>(data);
            // the new node should point to the past head
            newNode.next = head;
            // then the newNode becomes the head
            head = newNode;
        }

        // since an element was added increment size
        size++;
    }

    /**
     * @param data adds data to tail of list
     */
    public void addLast(E data) {
        // if there are no nodes, node to add is first
        if (head == null) {
            // for first element head and tail point to data
            head = tail = new Node<>(data);
        } else {
            Node<E> newNode = new Node<>(data);
            // point previous tail to new node
            tail.next = newNode;
            // make newNode the tail
            tail = newNode;
        }

        // since an element was added increment size
        size++;
    }

    /**
     * adds data to given position
     * @param index index for insertion
     * @param data element to insert
     */
    public void add(int index, E data) {
        // index is in between the nodes
        // make sure index is in valid range of linkedList
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException();
        }

        // special cases
        if (index == 0) {
            // same as adding element to head of linkedList
            addFirst(data);
        } else if (index == size) {
            // same as adding element to tail of linkedList
            addLast(data);
        } else {
            // adding somewhere after head or before tail
            // to get to an insertion point
            // the index of the node where you need to move until is index - 1
            Node<E> newNode = new Node<>(data);

            // initial pointer will be at head
            Node<E> pointer = head;
            // counter
            int i = 0;
            // go to the node at the location before the insertion
            while (i < index - 1) {
                pointer = pointer.next;
                i++;
            }

            // arrived at the node which should be to the left of insertion
            // newNode should point to pointer.next and pointer.next should point to newNode
            newNode.next = pointer.next;
            pointer.next = newNode;
            // increment size since element was added
            size++;
        }
    }

    /**
     * removes the head element
     */
    public void removeFirst() {
        // if linkedList is empty there is nothing to remove
        if (size == 0) {
            throw new NoSuchElementException();
        } else if (size == 1) {
            // there is a single element
            head = tail = null;
            size = 0;
        } else {
            // any other case
            // the new head should be where head is pointing to
            // previous head is lost to garbage collector
            head = head.next;
            // decrement size since removing an element
            size--;
        }
    }

    /**
     * removes the tail element
     */
    public void removeLast() {
        // if linked list is empty there is nothing to remove
        if (size == 0) {
            throw new NoSuchElementException();
        } else if (size == 1) {
            // there is a single element
            head = tail = null;
            size = 0;
        } else {
            // any other case
            // must traverse linked list until reaching the element before tail
            Node<E> pointer = head;
            // traverse until reaching element before tail
            while (pointer.next != tail) {
                pointer = pointer.next;
            }
            // arrived at the element before tail
            // set the new tail to that element and tail to null
            tail = pointer;
            tail.next = null;
            // decrement size since removing an element
            size--;
        }
    }

    /**
     * @param index removes element at index
     */
    public void remove(int index) {
        // index is directly the nodes
        // make sure index is in valid range
        if (!(index >= 0 && index < size)) {
            // index out of bounds
            throw new ArrayIndexOutOfBoundsException();
        } else if (size == 0) {
            // array is empty
            throw new NoSuchElementException();
        } else if (index == 0) {
            // same as removing first element
            removeFirst();
        } else if (index == size - 1) {
            // same as removing last
            removeLast();
        } else {
            // go to node before index and point it to node after index
            Node<E> pointer = head;
            // counter
            int i = 0;
            // loop until reaching node before the index
            while (i < index - 1) {
                pointer = pointer.next;
                // iter the amount this operation occurs
                i++;
            }
            // arived at index before 
            // assign that pointer next to the Node after index 
            pointer.next = pointer.next.next;
            // decrement size since removing element
            size--;
        }
    }

    /**
     * @return the head element
     */
    public E getFirst() {
        return head.data;
    }

    /**
     * @return the tail element
     */
    public E getLast() {
        return tail.data;
    }

    /**
     * @param index
     * @return element at given index
     */
    public E get (int index) {
        // validate index
        if (!(index >= 0 && index < size)) {
            throw new ArrayIndexOutOfBoundsException();
        } else if (size == 0) {
            throw new NoSuchElementException();
        }

        Node<E> pointer = head;
        int i = 0;
        // go through linkedList until arriving at element in index
        while (i < index) {
            pointer = pointer.next;
            i++;
        }

        return pointer.data; 
    }

    /**
     * @param index replaces element at index with data
     * @param data
     */
    public void set(int index, E data) {
        // validate index
        if (!(index >= 0 && index < size)) {
            throw new ArrayIndexOutOfBoundsException();
        } else if (size == 0) {
            throw new NoSuchElementException();
        }

        Node<E> pointer = head;
        int i = 0;
        // go through linkedList until arriving at element in index
        while (i < index) {
            pointer = pointer.next;
            i++;
        }
        // arrived at given index
        // replace pointer with data
        pointer.data = data;
    }

    /**
     * @param data
     * @return true if data is in linkedList
     */
    public boolean contains (E data) {
        // look through the linkedList checking if any of the point.data == data
        Node<E> pointer = head;
        // counter 
        int i = 0;
        while (i < size) {
            if (pointer.data == data) {
                // linkedList contains data
                return true;
            } else if (pointer.next == null) {
                // reached the end of the linkedList
                return false;
            }
            pointer = pointer.next;
            i++;
        }
        return false;
    }

    /**
     * @param data
     * @return index of data occurence from left -1 if not in list
     */
    public int indexOf(E data) {
        // starting from the left check if linkedList contains data
        Node<E> pointer = head;
        // counter 
        int i = 0;
        while (i < size) {
            if (pointer.data == data) {
                // linkedList contains data
                return i;
            } else if (pointer.next == null) {
                // reached the end of the linkedList
                return -1;
            }
            pointer = pointer.next;
            i++;
        }
        return -1;
    }

    /**
     * @param data 
     * @return index of data from the right
     */
    public int lastIndexOf(E data) {
        // starting from the right check if linkedList contains data
        Node<E> pointer = head;
        // counter 
        int i = size;
        while (i > 0) {
            if (pointer.data == data) {
                // linkedList contains data
                return i;
            } else if (pointer.next == null) {
                // reached the end of the linkedList
                return -1;
            }
            pointer = pointer.next;
            i--;
        }
        return -1;
    }

    /**
     * because the node will store elements with type E
     * it should also be of type E
     */
    class Node<E> {
        E data;
        // ref to next node
        Node<E> next;
        public Node(E data) {
            this.data = data;
        }

        // for simplicity adding a toString method
        public String toString() {
            return data.toString();
        }
    }

    public static void main(String[] args) {
        // testing the linked list
        MyLinkedList<Integer> linkedList = new MyLinkedList<>();
        // checking linked list value
        int contain = 1;
 
        
        
        
        for (int i = 0; i < 5; i++) {
            linkedList.addLast(i);
        }
        for (int i = 4; i >= 0; i--) {
            linkedList.addLast(i);
        }
        
        System.out.println(linkedList + " " + linkedList.size);
        System.out.printf("%d in list?: %b\n", contain, linkedList.contains(contain));
        System.out.printf("%d at index: %d\n", contain, linkedList.indexOf(contain));
        System.out.printf("%d at index: %d\n", contain, linkedList.lastIndexOf(contain));
    }
}
