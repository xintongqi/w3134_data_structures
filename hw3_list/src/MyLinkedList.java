import javax.swing.*;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Xintong Qi xq2224
 */

/**
 * Linked list implementation of the MyList interface.
 * @author Brian S. Borowski
 * @version 1.0 September 27, 2022
 */
public class MyLinkedList<E> implements MyList<E> {
    private Node head, tail;
    private int size;

    /**
     * Constructs an empty list.
     */
    public MyLinkedList() {
        head = tail = null;
        size = 0;
    }

    /**
     * Returns the number of elements in this list.
     * @return the number of elements in this list
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if this list contains no elements.
     * @return true if this list contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     * @param index    index of the element to return
     * @param element  element to be stored at the specified position
     * @return  the element at the specified position in this list
     * @throws  IndexOutOfBoundsException - if the index is out of range
     *          (index < 0 || index >= size())
     */
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", list size: " + size);
        }
        Node p = head; // the reference is copied
        for (int i = 0; i < index; i++, p = p.next);
        E oldElement = p.element;
        p.element = element;
        return oldElement;
    }

    /**
     * Returns the element at the specified position in this list.
     * @param index  index of the element to return
     * @return       the element at the specified position in this list
     * @throws       IndexOutOfBoundsException - if the index is out of range
     *               (index < 0 || index >= size())
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", list size: " + size);
        }
        Node p = head;
        for (int i = 0; i < index; i++, p = p.next);
        return p.element;
    }

    /**
     * Appends the specified element to the end of this list.
     * @param element  element to be appended to this list
     * @return true
     */
    public boolean add(E element) {
        Node n = new Node(element);
        if (head == null) {
            head = tail = n;
        } else {
            tail.next = n;
            tail = n;
        }
        size++;
        return true;
    }

    /**
     * Removes all of the elements from this list.
     */
    public void clear() {
        head = tail = null;
        size = 0;
    }

    public Iterator<E> iterator() {
        return new ListItr();
    }

    /**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   (index < 0 || index > size())
     *                                   The exception message must be:
     *                                   "Index: " + index + ", list size: " + size
     */
    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", list size: " + size);
        }
        Node n = new Node(element);
        if (head == null){ // add to an empty list
            head = tail = n;
        } else if (index==0) { // add to a non-empty list at 0
            // The reason why this work is that head is just a pointer
            // why? because we never did head = new Node() even though it's of type Node
            // in the following lines, we are not modifying the content of head
            // we are modifying where it points to, which won't affect p at all
            // if head has a content and we change it, p will update as well
            Node p = head;
            head = n;
            n.next = p;
        } else { // add to a non-empty list at a non-0 index
            Node p = head;
            for (int i = 0; i < index-1; i++, p = p.next);
            Node q = p.next;
            p.next=n;
            n.next=q;
        }
        size++;
    }

    /**
     * Removes the element at the specified position in this list.
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   (index < 0 || index >= size())
     *                                   The exception message must be:
     *                                   "Index: " + index + ", list size: " + size
     */
    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", list size: " + size);
        }
        E oldElement;
        if (size==1){ // remove from a list with one element
            oldElement = head.element;
            head=tail=null;
        } else if (index==0) { // remove the first element
            oldElement = head.element;
            head=head.next;
        } else { // remove element at a non-0 index
            Node p = head;
            for (int i = 0; i < index-1; i++, p = p.next);
            oldElement = p.next.element;
            p.next = p.next.next;
        }
        size--;
        return oldElement;
    }

    /**
     * Returns the index of the first occurrence of the specified element in
     * this list, or -1 if this list does not contain the element. More
     * formally, returns the lowest index i such that Objects.equals(o, get(i)),
     * or -1 if there is no such index.
     *
     * @param element element to search for
     * @return the index of the first occurrence of the specified element in
     * this list, or -1 if this list does not contain the element
     */
    @Override
    public int indexOf(E element) {
        ListItr listItr = new ListItr();
        int i=0;
        while (listItr.hasNext()){
            if (Objects.equals(element,listItr.current.element)){
                return i;
            }
            listItr.next();
            i++;
        }
        return -1;
    }

    /**
     * Returns an array of indexes of each occurrence of the specified element
     * in this list, in ascending order. If the specified element is not found,
     * a non-null empty array (not null) is returned.
     *
     * @param element element to search for
     * @return an array of each occurrence of the specified element in this
     * list
     */
    @Override
    public int[] indexesOf(E element) {
        int[] temp = new int[size]; // temporary array to store indexes, at most size occurences
        ListItr listItr = new ListItr();
        int i=0, j=0;
        while (listItr.hasNext()){
            if (Objects.equals(element,listItr.current.element)){
                temp[j] = i;
                j++;
            }
            listItr.next();
            i++;
        }
        // copy it to an array with length==number of founds
        int[] result = new int[j];
        for (int k=0; k<j; k++){
            result[k] = temp[k];
        }
        temp=null; // GC the temporary array
        return result;
    }

    /**
     * Reverses the data in the list.
     * For MyArrayList, the data inside the underlying is moved. For
     * MyLinkedList, the tail must become the head, and all the pointers are
     * reversed. Both implementations must run in Theta(n).
     */
    @Override
    public void reverse() {
//        Ok I'm lost, what the fuck are you doing here??
//        How would anything like this fucking work? :)

//        if (size <= 1){
//            Node n = head;
//            head = tail;
//            tail = n;
//        } else {
//            Node q = head;
//            for (int i=0; i<size; i++){
//                Node p = q;
//                p.next.next = p;
//                q = q.next;
//            }
//            Node n = head;
//            head = tail;
//            tail = n;
//            tail.next = null;
//        }
        if (size==0) { // empty list do nothing
            return;
        } else {
            Node cur = head;
            Node pre = null;
            Node nxt;

            tail = head;
            // TODO: !!! so this line is where you fucked up
            //  different from C, which only has a head but not tail
            //  you have to set the tail=head in Java.
            //  otherwise adding to it won't work in the future

            while (cur != null) {
                nxt = cur.next;
                cur.next = pre;
                pre = cur;
                cur = nxt;
            }
            head = pre;
        }
    }

    /**
     * Returns a string representation of the list. The string will begin with
     * a '[' and end with a ']'. Inside the square brackets will be a comma-
     * separated list of values, such as [Brian, Susan, Jamie].
     * @return a string representation of the list.
     */
    @Override
    public  String toString(){
        String result = "[";
        ListItr listItr = new ListItr();
        while (listItr.hasNext()){
            if (listItr.current==head){
                result += listItr.next().toString();
            } else {
                result += (", " + listItr.next().toString());
            }
        }
        return result + "]";
    }

    private class ListItr implements Iterator<E> {
        private Node current;

        ListItr() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            E element = current.element;
            current = current.next;
            return element;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class Node {
        Node next;
        E element;

        public Node(E element) {
            this.element = element;
        }
    }
}
