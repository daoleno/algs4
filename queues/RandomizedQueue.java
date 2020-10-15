import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int defaultCapacity = 32;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[defaultCapacity];
        size = 0;

    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        queue[size++] = item;

        if (size == queue.length) {
            resize(size * 2);
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = StdRandom.uniform(0, size);
        Item item = queue[index];

        // remove indexed item, copy oldQueue to queue.
        Item[] oldQueue = queue;
        for (int i = 0, j = 0; j < size; i++, j++) {
            if (j == index) {
                j++;
            }
            queue[i] = oldQueue[j];
        }
        size--;

        if (size == queue.length / 3) {
            resize(queue.length / 2);
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = StdRandom.uniform(0, size);
        Item item = queue[index];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = queue[i];
        }
        queue = copy;
    }

    private Item[] copy(Item[] src) {
        Item[] copy = (Item[]) new Object[src.length];
        for (int i = 0; i < src.length; i++) {
            copy[i] = src[i];
        }
        return copy;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] shuffledQueue;
        private int index;

        public RandomizedQueueIterator() {
            // deep copy
            shuffledQueue = copy(queue);
            StdRandom.shuffle(shuffledQueue, 0, size);
            index = 0;
        }

        public boolean hasNext() {
            return index != size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return shuffledQueue[index++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
    }

}
