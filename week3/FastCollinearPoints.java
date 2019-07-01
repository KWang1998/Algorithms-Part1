/* *****************************************************************************
 *  Name:K 
 *  Date:
 *  Description:Algorithms Part1 Week3
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private int nOfS;
    private Node tail;
    private Node first;

    private class Node {
        LineSegment item;
        Node next;

        public Node(LineSegment a) {
            item = a;
            next = null;
        }
    }

    public FastCollinearPoints(
            Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        nOfS = 0;
        int n = points.length;

        Point[] temp = new Point[n];
        for (int p = 0; p < n; p++) {
            if (points[p] == null) throw new IllegalArgumentException();
            for (int q = p + 1; q < n; q++) {
                if (points[q] == null)
                    throw new IllegalArgumentException();
                if (points[p].compareTo(points[q]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
            temp[p] = points[p];
        }
        Arrays.sort(temp);

        Point[] comp = new Point[n - 1];
        for (int i = 0; i < n - 1; i++) {
            int m = 0;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    comp[m] = temp[j];
                    m++;
                }
            }

            Arrays.sort(comp, temp[i].slopeOrder());
            Point min = null;
            Point max = null;
            int count = 1;

            for (int k = 0; k < n - 1; k++) {
                if ((k != n - 2) && (temp[i].slopeTo(comp[k]) == temp[i]
                        .slopeTo(comp[k + 1]))) {
                    if (min == null) {
                        if (temp[i].compareTo(comp[k]) < 0) {
                            min = temp[i];
                            max = comp[k];
                        }
                        else {
                            min = comp[k];
                            max = temp[i];
                        }
                    }
                    if (max.compareTo(comp[k + 1]) < 0) {
                        max = comp[k + 1];
                    }
                    if (min.compareTo(comp[k + 1]) > 0) {
                        min = comp[k + 1];
                    }
                    count++;
                }
                else {
                    if (count > 2 && temp[i].compareTo(min) == 0) {
                        nOfS++;
                        if (tail == null) {
                            tail = new Node(new LineSegment(min, max));
                            first = tail;
                        }
                        else {
                            Node temp1 = new Node(new LineSegment(min, max));
                            tail.next = temp1;
                            tail = temp1;
                        }
                    }
                    min = null;
                    max = null;
                    count = 1;
                }
            }
        }

    }    // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return nOfS;
    }        // the number of line segments

    public LineSegment[] segments() {
        LineSegment[] lSegments = new LineSegment[nOfS];
        Node cur;
        cur = first;
        for (int i = 0; i < nOfS; i++) {
            lSegments[i] = cur.item;
            cur = cur.next;
        }
        return lSegments;
    }                // the line segments

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
