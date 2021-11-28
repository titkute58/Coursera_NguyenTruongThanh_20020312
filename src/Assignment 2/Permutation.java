import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Scanner;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> strings = new RandomizedQueue<>();
        while(!StdIn.isEmpty()) {
            strings.enqueue(StdIn.readString());
        }

        for(int i = 1; i <= k; i++) {
            StdOut.println(strings.dequeue());
        }
    }
}
