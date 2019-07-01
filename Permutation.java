/* *****************************************************************************
 *  Name:K Wang
 *  Date:03/17/2019
 *  Description:Algorithms Part1 Week2
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> test = new RandomizedQueue<String>();
        int input = Integer.parseInt(args[0]);

        while (!StdIn.isEmpty()) {
            String temp = StdIn.readString();
            test.enqueue(temp);
        }
        for (int i = 0; i < input; i++) {
            String temp2 = test.dequeue();
            StdOut.println(temp2);
        }
    }
}
