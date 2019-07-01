/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root;
    private int treeSize;

    private class Node {
        private Node left, right;
        private Point2D point;
        private RectHV rect;
        private boolean isV;

        public Node(Point2D p, Node leftN, Node rightN, RectHV rec, boolean isVertical) {
            this.left = leftN;
            this.right = rightN;
            this.point = p;
            this.rect = rec;
            this.isV = isVertical;
        }
    }

    public KdTree() {
        this.root = null;
        this.treeSize = 0;
    }                              // construct an empty set of points

    public boolean isEmpty() {
        return (treeSize == 0);
    }                     // is the set empty?

    public int size() {
        return treeSize;
    }                        // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        if (root == null) {
            root = new Node(p, null, null, new RectHV(0, 0, 1, 1), true);
            treeSize++;
        }
        else {
            Node temp = root;
            while (true) {
                if (temp.point.equals(p)) return;
                if (temp.isV) {
                    if (p.x() < temp.point.x()) {
                        if (temp.left == null) {
                            RectHV tempR = new RectHV(temp.rect.xmin(), temp.rect.ymin(),
                                                      temp.point.x(), temp.rect.ymax());
                            temp.left = new Node(p, null, null, tempR, false);
                            treeSize++;
                            return;
                        }
                        else temp = temp.left;
                    }
                    else {
                        if (temp.right == null) {
                            RectHV tempR = new RectHV(temp.point.x(), temp.rect.ymin(),
                                                      temp.rect.xmax(), temp.rect.ymax());
                            temp.right = new Node(p, null, null, tempR, false);
                            treeSize++;
                            return;
                        }
                        temp = temp.right;
                    }
                }
                else {
                    if (p.y() < temp.point.y()) {
                        if (temp.left == null) {
                            RectHV tempR = new RectHV(temp.rect.xmin(), temp.rect.ymin(),
                                                      temp.rect.xmax(), temp.point.y());
                            temp.left = new Node(p, null, null, tempR, true);
                            treeSize++;
                            return;
                        }
                        temp = temp.left;
                    }
                    else {
                        if (temp.right == null) {
                            RectHV tempR = new RectHV(temp.rect.xmin(), temp.point.y(),
                                                      temp.rect.xmax(), temp.rect.ymax());
                            temp.right = new Node(p, null, null, tempR, true);
                            treeSize++;
                            return;
                        }
                        temp = temp.right;
                    }
                }
            }
        }
    }             // add the point to the set (if it is not already in the set)

    private boolean contains(Point2D p, Node start) {
        if (start == null) return false;
        if (start.point.equals(p)) return true;
        if (start.isV) {
            if (start.point.x() > p.x()) return contains(p, start.left);
            else return contains(p, start.right);
        }
        else {
            if (start.point.y() > p.y()) return contains(p, start.left);
            else return contains(p, start.right);
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        if (treeSize == 0) return false;
        else return contains(p, root);
    }           // does the set contain point p?

    private void draw(Node temp) {
        if (temp.left != null) draw(temp.left);
        if (temp.right != null) draw(temp.right);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(temp.point.x(), temp.point.y());
        if (temp.isV) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(temp.point.x(), temp.rect.ymin(), temp.point.x(), temp.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(temp.rect.xmin(), temp.point.y(), temp.rect.xmax(), temp.point.y());
        }

    }

    public void draw() {
        StdDraw.rectangle(1, 1, 1, 1);
        if (isEmpty()) return;
        draw(root);
    }                        // draw all points to standard draw

    private void range(RectHV rect, Node temp, Stack<Point2D> stackR) {
        if (rect.contains(temp.point)) stackR.push(temp.point);
        if ((temp.left != null) && (temp.left.rect.intersects(rect)))
            range(rect, temp.left, stackR);
        if ((temp.right != null) && (temp.right.rect.intersects(rect)))
            range(rect, temp.right, stackR);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new java.lang.IllegalArgumentException();
        Stack<Point2D> insideRect = new Stack<Point2D>();
        if (isEmpty()) return insideRect;
        range(rect, root, insideRect);
        return insideRect;
    }            // all points that are inside the rectangle (or on the boundary)

    private Point2D nearest(Point2D p, Node temp, Point2D minP) {
        if (temp == null) return minP;
        if (minP.distanceSquaredTo(p) > temp.point.distanceSquaredTo(p)) {
            minP = temp.point;
        }
        if (temp.isV) {
            if (p.x() > temp.point.x()) {
                minP = nearest(p, temp.right, minP);
                if ((temp.left != null) && (temp.left.rect.distanceSquaredTo(p) < minP
                        .distanceSquaredTo(p))) {
                    minP = nearest(p, temp.left, minP);
                }
            }
            else {
                minP = nearest(p, temp.left, minP);
                if ((temp.right != null) && (temp.right.rect.distanceSquaredTo(p) < minP
                        .distanceSquaredTo(p))) {
                    minP = nearest(p, temp.right, minP);
                }
            }
        }
        else {
            if (p.y() > temp.point.y()) {
                minP = nearest(p, temp.right, minP);
                if ((temp.left != null) && (temp.left.rect.distanceSquaredTo(p) < minP
                        .distanceSquaredTo(p))) {
                    minP = nearest(p, temp.left, minP);
                }
            }
            else {
                minP = nearest(p, temp.left, minP);
                if ((temp.right != null) && (temp.right.rect.distanceSquaredTo(p) < minP
                        .distanceSquaredTo(p))) {
                    minP = nearest(p, temp.right, minP);
                }
            }
        }
        return minP;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        if (isEmpty()) return null;
        return nearest(p, root, root.point);
    }            // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {

    }                 // unit testing of the methods (optional)
}
