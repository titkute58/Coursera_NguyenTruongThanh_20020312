import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Node(Item item) {
            data = item;
        }

        Node(Item item, Node next) {
            data = item;
            this.next = next;
        }

        Node (Item item, Node next, Node prev) {
            data = item;
            this.next = next;
            this.prev = prev;
        }

        Node prev;
        Node next;
        Item data;
    }

    private Node first;
    private Node last;
    private int n;


    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (first == null || last == null) {
            first = new Node(item, null);
            last = first;
        } else {
            Node tmp = first;
            first = new Node(item, tmp);
            tmp.prev = first;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (last == null || first == null) {
            first = new Node(item, null);
            last = first;
        } else {
            Node tmp = last;
            last = new Node(item, null, tmp);
            tmp.next = last;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if(isEmpty()) throw new NoSuchElementException();

        Item item = first.data;
        first = first.next;
        n--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if(isEmpty()) throw new NoSuchElementException();

        if (last == null) {
            throw new NoSuchElementException();
        }
        Item item = last.data;
        if(n == 1) {
            first = null;
            last = null;
        }
        else {
            last = last.prev;
            last.next = null;
        }
        n--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LListIterator();
    }

    private class LListIterator implements Iterator<Item> {
        Node cur = first;

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
               throw new NoSuchElementException();
            }
            Item item = cur.data;
            cur = cur.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args){
        Deque<Integer> dq = new Deque<>();
        dq.addFirst(1);
        printDeque(dq);
        dq.addLast(2);
        printDeque(dq);
        dq.removeFirst();
        printDeque(dq);
        dq.removeLast();
        printDeque(dq);
    }
    private static void printDeque(Deque<Integer> dq) {
        Iterator<Integer> i = dq.iterator();
        while (i.hasNext()) {
            StdOut.printf("%d\n", i.next());
        }
    }
}