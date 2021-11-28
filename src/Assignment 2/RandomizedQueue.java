import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        n = 0;
        items = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (n == items.length) resize(2 * n);

        items[n] = item;
        n++;
    }

    private void resize(int cap) {
        Item[] tmp = (Item[]) new Object[cap];
        for(int i = 0; i < n; i++) {
            tmp[i] = items[i];
        }
        items = tmp;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())  {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniform(n);
        Item item = items[index];
        items[index] = items[--n];
        items[n] = null;
        if(n > 0 && n == items.length/4) resize(items.length/2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())  {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniform(n);
        return items[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        int i = 0;
        int[] ran;

        public ArrayIterator() {
            ran = new int[n];
            for(int a = 0;a < n;a++) {
                ran[a] = a;
            }
            StdRandom.shuffle(ran);
            i = 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return i < n;
        }

        @Override
        public Item next(){
            if(!hasNext()) throw new NoSuchElementException();
            return items[ran[i++]];
        }
    }
    // unit testing (required)
    public static void main(String[] args){
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        System.out.println(queue.size());
        System.out.println(queue.isEmpty());
        System.out.println(queue.isEmpty());
        System.out.println(queue.isEmpty());
        System.out.println(queue.size());
        queue.enqueue(12);

//        RandomizedQueue<Integer> ranQueue = new RandomizedQueue<>();
//        ranQueue.enqueue(1);
//        printRan(ranQueue);
//        ranQueue.enqueue(2);
//        printRan(ranQueue);
//        ranQueue.enqueue(3);
//        ranQueue.enqueue(4);
//        ranQueue.enqueue(5);
//        ranQueue.enqueue(6);
//        StdOut.println(ranQueue.dequeue());
//        StdOut.println(ranQueue.sample());
//        printRan(ranQueue);
    }
    private static void printRan(RandomizedQueue<Integer> ran) {
        for(Integer it : ran)
            StdOut.println(it);
    }
}