import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }


    public String toString() {
        String ans = "(" + this.x + ", " + this.y + ")";
        return ans;
    }

    public int compareTo(Point that) {
        if (that.y == this.y) {
            if (this.x > that.x) {
                return 1;
            } else if (this.x == that.x) {
                return 0;
            } else {
                return -1;
            }
        }
        if (this.y > that.y) {
            return 1;
        } else {
            return -1;
        }
    }

    public double slopeTo(Point that) {
        if (that.x == this.x) {
            if(that.y == this.y) return Double.NEGATIVE_INFINITY;
            return Double.POSITIVE_INFINITY;
        }
        if(this.y == that.y) return +0.0;

        return (double)(that.y - this.y) / (that.x - this.x);
    }

    public Comparator<Point> slopeOrder() {
        return new Order();
    }

    private class Order implements Comparator<Point> {

        @Override
        public int compare(Point o1, Point o2) {
            double p1 = slopeTo(o1);
            double p2 = slopeTo(o2);
            if(p1 > p2) return 1;
            else if(p1 == p2) return 0;
            else return -1;
        }
    }
}