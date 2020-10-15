import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        // Read text
        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }

        // Print random item(repeat)
        // int upperBound = k < queue.size() ? k : queue.size();
        // for (int i = 0; i < upperBound; i++) {
        // StdOut.println(queue.sample());
        // }

        // Print random item(uniformly)
        int index = 0;
        for (String item : queue) {
            if (index == k) {
                break;
            }

            StdOut.println(item);
            index++;
        }
    }
}