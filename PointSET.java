/* *****************************************************************************
 *  Name:K
 *  Date:04/03/2019
 *  Description:Algorithms Part1 Week5
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;

public class PointSET {
    private SET<Point2D> mySet;

    public PointSET() {
        mySet = new SET<Point2D>();
    }                             // construct an empty set of points

    public boolean isEmpty() {
        return mySet.isEmpty();
    }                     // is the set empty?

    public int size() {
        return mySet.size();
    }                        // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        if (!mySet.contains(p)) mySet.add(p);
    }             // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        return mySet.contains(p);
    }           // does the set contain point p?

    public void draw() {
        for (Point2D point : mySet) {
            point.draw();
        }
    }                        // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new java.lang.IllegalArgumentException();
        Stack<Point2D> insideRect = new Stack<Point2D>();
        for (Point2D point : mySet) {
            if (rect.contains(point)) insideRect.push(point);
        }
        return insideRect;
    }             // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        double minDistance = Double.POSITIVE_INFINITY;
        Point2D minDPoint = null;
        for (Point2D point : mySet) {
            if (minDPoint == null) {
                minDistance = p.distanceTo(point);
                minDPoint = point;
            }
            else {
                if (p.distanceTo(point) < minDistance) {
                    minDistance = p.distanceTo(point);
                    minDPoint = point;
                }
            }
        }
        return minDPoint;
    }             // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {

    }                 // unit testing of the methods (optional)
}
