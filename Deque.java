/* *****************************************************************************
 *  Name:K Wang
 *  Date:03/16/2019
 *  Description:Algorithms Part1 Week2
 **************************************************************************** */

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private int n;
    private Node first, last;

    private class Node {
        private Item item;
        private Node next;
        private Node before;
    }

    public Deque() {
        this.first = null;
        this.last = null;
        this.n = 0;
    }                           // construct an empty deque

    public boolean isEmpty() {
        return n == 0;
    }                // is the deque empty?

    public int size() {
        return n;
    }                       // return the number of items on the deque

    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        first.before = null;
        if (isEmpty()) {
            last = first;
        }
        else {
            oldfirst.before = first;
        }
        n++;
    }          // add the item to the front

    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.before = oldlast;
        last.next = null;
        if (isEmpty()) {
            first = last;
        }
        else {
            oldlast.next = last;
        }
        n++;
    }          // add the item to the end

    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Item temp = first.item;
        first = first.next;
        n--;
        if (isEmpty()) {
            last = null;
        }
        else {
            first.before = null;
        }
        return temp;
    }                // remove and return the item from the front

    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item temp = last.item;
        last = last.before;
        n--;
        if (isEmpty()) {
            first = null;
        }
        else {
            last.next = null;
        }
        return temp;
    }                 // remove and return the item from the end

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }         // return an iterator over items in order from front to end

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item temp = current.item;
            current = current.next;
            return temp;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {

    }  // unit testing (optional)
}
