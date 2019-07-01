/* *****************************************************************************
 *  Name:K
 *  Date:
 *  Description:Algorithms Part1 Week2
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] randomizedQueue;
    private int n;
    // private int tail;

    public RandomizedQueue() {
        randomizedQueue = (Item[]) new Object[1];
        n = 0;
    }                 // construct an empty randomized queue

    public boolean isEmpty() {
        return n == 0;
    }                // is the randomized queue empty?

    public int size() {
        return n;
    }                        // return the number of items on the randomized queue

    public void enqueue(Item item) {
        if (item == null) throw new java.lang.IllegalArgumentException();
        if (n == randomizedQueue.length) resize(2 * randomizedQueue.length);
        randomizedQueue[n++] = item;
    }           // add the item

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = randomizedQueue[i];
        }
        randomizedQueue = copy;
    }

    public Item dequeue() {
        if (n == 0) throw new java.util.NoSuchElementException();
        int t = (StdRandom.uniform(n)) % n;
        Item temp = randomizedQueue[t];
        if (t == n - 1) {
            randomizedQueue[n - 1] = null;
        }
        else {
            randomizedQueue[t] = randomizedQueue[n - 1];
            randomizedQueue[n - 1] = null;
        }
        n--;

        if (n > 0 && n == randomizedQueue.length / 4) resize(randomizedQueue.length / 2);
        return temp;
    }                    // remove and return a random item

    public Item sample() {
        if (n == 0) throw new java.util.NoSuchElementException();
        int t = (StdRandom.uniform(n)) % n;
        Item temp = randomizedQueue[t];
        return temp;
    }                    // return a random item (but do not remove it)

    public Iterator<Item> iterator() {
        return new RQIterator();
    }         // return an independent iterator over items in random order

    private class RQIterator implements Iterator<Item> {
        private Item[] temp3;
        private int sum;

        public RQIterator() {
            temp3 = (Item[]) new Object[n];
            for (int i = 0; i < n; i++) {
                temp3[i] = randomizedQueue[i];
            }
            StdRandom.shuffle(temp3);
            sum = n;
        }

        public boolean hasNext() {
            return sum != 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item temp = temp3[sum - 1];
            sum--;
            return temp;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {

    }  // unit testing (optional)
}
