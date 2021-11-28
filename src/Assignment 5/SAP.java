import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class SAP {

    private Digraph dg;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }
        dg = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        //min
        if (!checkValid(v) || !checkValid(w)) {
            throw new IllegalArgumentException();
        }
        return bfs(new BreadthFirstDirectedPaths(dg, v), new BreadthFirstDirectedPaths(dg, w))[1];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        //ancestor
        if (!checkValid(v) || !checkValid(w)) {
            throw new IllegalArgumentException();
        }
        return bfs(new BreadthFirstDirectedPaths(dg, v), new BreadthFirstDirectedPaths(dg, w))[0];
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (!checkValid(v) || !checkValid(w)) {
            throw new IllegalArgumentException();
        }
        Iterator<Integer> it1 = v.iterator();
        Iterator<Integer> it2 = w.iterator();
        if (!it1.hasNext() || !it2.hasNext()) return -1;
        return bfs(new BreadthFirstDirectedPaths(dg, v), new BreadthFirstDirectedPaths(dg, w))[1];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (!checkValid(v) || !checkValid(w)) {
            throw new IllegalArgumentException();
        }
        Iterator<Integer> it1 = v.iterator();
        Iterator<Integer> it2 = w.iterator();
        if (!it1.hasNext() || !it2.hasNext()) return -1;
        return bfs(new BreadthFirstDirectedPaths(dg, v), new BreadthFirstDirectedPaths(dg, w))[0];
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

    private int[] bfs(BreadthFirstDirectedPaths bfs1, BreadthFirstDirectedPaths bfs2) {
        int min = Integer.MAX_VALUE;
        int ancestor = 0;
        int[] ans = new int[2];
        ans[0] = -1;
        ans[1] = -1;
        for (int i = 0; i < dg.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                int distance = bfs1.distTo(i) + bfs2.distTo(i);
                if (distance < min) {
                    min = distance;
                    ancestor = i;
                }
            }
        }
        if (min != Integer.MAX_VALUE) {
            ans[0] = ancestor;
            ans[1] = min;
            return ans;
        }
        return ans;
    }
    /** Check valid */
    private boolean checkValid(int v) {
        return (v > -1 && v < this.dg.V());
    }

    private boolean checkValid(Iterable<Integer> v) {
        for (Integer i : v) {
            if (i < 0 || i >= this.dg.V()) {
                return false;
            }
        }
        return true;
    }
}
