import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    private Node first, last;
    private int size;

    // construct an empty deque
    public Deque() {
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (size == 0) {
            // create first node
            first = new Node();
            first.item = item;
            last = first;
            size++;
            return;
        }

        // create other node
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;

        // bi-directional connection
        first.next = oldfirst;
        oldfirst.prev = first;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (size == 0) {
            // create first node
            last = new Node();
            last.item = item;
            first = last;
            size++;
            return;
        }

        // create other node
        Node oldlast = last;
        last = new Node();
        last.item = item;

        // bi-directional connection
        last.prev = oldlast;
        oldlast.next = last;

        last.next = null;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (size == 1) {
            // handle only one item
            Item item = first.item;
            first = null;
            last = null;
            size--;
            return item;
        }
        Item item = first.item;
        first = first.next;
        first.prev = null;
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (size == 1) {
            // handle only one item
            Item item = first.item;
            first = null;
            last = null;
            size--;
            return item;
        }

        Item item = last.item;
        last = last.prev;
        last.next = null;
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        String input[] = new String[] { "The", "Dark", "Side", "of", "the", "Moon" };

        // Add
        StdOut.print("********* Add deque *********\n");
        for (String i : input) {
            deque.addFirst(i);
            deque.addLast(i);
        }
        // print deque
        for (String i : deque) {
            StdOut.printf("%s ", i);
        }

        // Remove
        StdOut.print("\n********* Remove deque *********\n");
        for (String i : deque) {
            StdOut.printf("%s ", deque.removeFirst());
        }

        Deque<Integer> dequeInt = new Deque<Integer>();
        dequeInt.addFirst(1);
        dequeInt.addFirst(2);
        dequeInt.removeLast();
        dequeInt.removeLast();

    }

}