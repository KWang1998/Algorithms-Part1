/* *****************************************************************************
 *  Name:K 
 *  Date:
 *  Description:Algorithms Part1 Week3
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private int nOfS;
    private Node tail;
    private Node first;

    private class Node {
        private LineSegment item;
        private Node next;

        public Node(LineSegment a) {
            item = a;
            next = null;
        }
    }

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        nOfS = 0;
        int sum = points.length;

        Point[] temp = new Point[sum];
        for (int p = 0; p < sum; p++) {
            if (points[p] == null)
                throw new IllegalArgumentException();
            for (int q = p + 1; q < sum; q++) {
                if (points[q] == null)
                    throw new IllegalArgumentException();
                if (points[p].compareTo(points[q]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
            temp[p] = points[p];
        }
        Arrays.sort(temp);
        for (int i = 0; i < sum - 3; i++) {
            for (int j = i + 1; j < sum - 2; j++) {
                for (int k = j + 1; k < sum - 1; k++) {
                    for (int l = k + 1; l < sum; l++) {
                        if ((temp[i].slopeTo(temp[j]) == temp[j].slopeTo(temp[k]))
                                && (temp[j].slopeTo(temp[k]) == temp[k]
                                .slopeTo(temp[l]))) {
                            nOfS++;
                            if (tail == null) {
                                tail = new Node(new LineSegment(temp[i], temp[l]));
                                first = tail;
                            }
                            else {
                                Node temp1 = new Node(new LineSegment(temp[i], temp[l]));
                                tail.next = temp1;
                                tail = temp1;
                            }
                        }
                    }
                }
            }
        }
    }    // finds all line segments containing 4 points

    public int numberOfSegments() {
        return nOfS;
    }       // the number of line segments

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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
