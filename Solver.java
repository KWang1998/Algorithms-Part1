/* *****************************************************************************
 *  Name:K Wang
 *  Date:03/28/2019
 *  Description:Algorithms Part1 Week4
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private boolean solvable;
    private int move;
    private Node current;


    private class Node implements Comparable<Node> {
        private int priority;
        private Node parent;
        private Board boa;
        private int currmove;

        public Node(int move, Node parent, Board bo) {
            this.currmove = move;
            this.priority = currmove + bo.manhattan();
            this.parent = parent;
            this.boa = bo;
        }

        public int compareTo(Node that) {
            return Integer.compare(this.priority, that.priority);
        }
    }

    public Solver(Board initial) {
        if (initial == null) throw new java.lang.IllegalArgumentException();
        Node currentTwin;
        MinPQ<Node> queue = new MinPQ<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Integer.compare(o1.priority, o2.priority);
            }
        });
        MinPQ<Node> queueTwin = new MinPQ<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Integer.compare(o1.priority, o2.priority);
            }
        });
        move = 0;
        int moveT = 0;
        this.current = null;

        current = new Node(0, null, initial);
        currentTwin = new Node(0, null, initial.twin());

        while (!(current.boa.isGoal() || currentTwin.boa.isGoal())) {
            for (Board thisNeigh : current.boa.neighbors()) {
                if (!((current.parent != null) && (thisNeigh.equals(current.parent.boa)))) {
                    queue.insert(new Node(move + 1, current, thisNeigh));
                }
            }
            for (Board thisNeighT : currentTwin.boa.neighbors()) {
                if (!((currentTwin.parent != null) && (thisNeighT
                        .equals(currentTwin.parent.boa)))) {
                    queueTwin.insert(new Node(moveT + 1, currentTwin, thisNeighT));
                }
            }
            current = queue.delMin();
            currentTwin = queueTwin.delMin();
            move = current.currmove;
            moveT = currentTwin.currmove;
        }
        if (current.boa.hamming() == 0) {
            solvable = true;
        }
        else {
            solvable = false;
            move = -1;
        }
    }         // find a solution to the initial board (using the A* algorithm)

    public boolean isSolvable() {
        return solvable;
    }            // is the initial board solvable?

    public int moves() {
        if (!solvable) return -1;
        return move;
    }                                      // min number of moves to solve initial board; -1 if unsolvable

    public Iterable<Board> solution() {
        Stack<Board> theSolution = new Stack<Board>();
        Node temp;
        if (solvable) {
            temp = current;
        }
        else {
            return null;
        }
        while (temp != null) {
            theSolution.push(temp.boa);
            temp = temp.parent;
        }
        return theSolution;
    }      // sequence of boards in a shortest solution; null if unsolvable

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    } // solve a slider puzzle (given below)
}

