import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private MinPQ<SearchNode> pq;
    private MinPQ<SearchNode> twin;
    private Stack<Board> solutionsBoard;
    private int n;
    private Board initial;
    private Board goal;
    private SearchNode end;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if(initial == null) {
            throw new IllegalArgumentException();
        }
        this.initial = initial;
        n = initial.dimension();
        goal = new Board(goalBoard());
        pq = new MinPQ<SearchNode>();
        twin = new MinPQ<SearchNode>();
        SearchNode min;
        SearchNode minTwin;
        pq.insert(new SearchNode(initial, 0, null));
        twin.insert(new SearchNode(initial.twin(), 0, null));
        while(!pq.min().board.equals(goal) && !twin.min().board.equals(goal)) {
            min = pq.min();
            minTwin = twin.min();
            pq.delMin();
            twin.delMin();
            for (Board neighbor: min.board.neighbors()) { //int i = 0; i < minNode.board.neighbors().size();i++
                if (min.moves == 0) {
                    pq.insert(new SearchNode(neighbor, min.moves + 1, min));
                }
                else if (!neighbor.equals(min.prev.board)) {
                    pq.insert(new SearchNode(neighbor, min.moves + 1, min));
                }
            }
            for (Board neighbor: minTwin.board.neighbors()) {
                if (minTwin.moves == 0) {
                    twin.insert(new SearchNode(neighbor, minTwin.moves + 1, minTwin));
                }
                else if (!neighbor.equals(minTwin.prev.board)) {
                    twin.insert(new SearchNode(neighbor, minTwin.moves + 1, minTwin));
                }
            }
        }
    }

    private int[][] goalBoard() {
        int[][] goal = new int[n][n];
        for(int i = 0;i < n;i++) {
            for(int j = 0;j < n;j++) {
                goal[i][j] = goalPos(i,j);
            }
        }
        goal[n-1][n-1] = 0;
        return goal;
    }
    private int goalPos(int row, int col) {
        return row * n + col + 1;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        if (pq.min().board.equals(goal)) {
            return true;
        }
        return false;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        return pq.min().moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if(!isSolvable()) return null;
        solutionsBoard = new Stack<Board>();
        SearchNode cur = pq.min();
        while (cur.prev != null) {
            solutionsBoard.push(cur.board);
            cur = cur.prev;
        }
        solutionsBoard.push(initial);
        return solutionsBoard;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        //In in = new In(args[0]);
        int n = StdIn.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = StdIn.readInt();
        Board initial = new Board(tiles);

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
    }

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private int priority;
        private SearchNode prev;

        public SearchNode(Board board, int moves, SearchNode previousNode) {
            this.board = board;
            this.moves = moves;
            priority = moves + board.manhattan();
            this.prev = previousNode;
        }

        @Override
        public int compareTo(SearchNode that) {
            if(this.priority > that.priority) return 1;
            else if (this.priority == that.priority) return 0;
            return -1;
        }
    }

}