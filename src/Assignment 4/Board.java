import edu.princeton.cs.algs4.Stack;

import java.util.ArrayList;
import java.util.Arrays;


public class Board {

    private int[][] tiles;
    private int n;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int[n][n];
        for(int i = 0;i < n;i++) {
            for(int j = 0;j < n;j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        String ans = "" + dimension() + "\n";
        for(int i = 0;i < n;i++) {
            for(int j = 0;j < n;j++) {
                ans += tiles[i][j] + " ";
            }
            ans += "\n";
        }
        return ans;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        for(int i = 0;i < n;i++) {
            for(int j = 0;j < n;j++) {
                if(tiles[i][j] != goalPos(i,j) && tiles[i][j] != 0) {
                    count++;
                }
            }
        }
        return count;
    }

    private int goalPos(int row, int col) {
        return row * n + col + 1;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int ans = 0;
        for(int i = 0;i < n;i++) {
            for(int j = 0;j < n;j++) {
                if(tiles[i][j] != goalPos(i,j) && tiles[i][j] != 0) {
                    // abs(x1-x2) + abs(y1-y2)
                    ans += Math.abs( (tiles[i][j] - 1) / n - i) + Math.abs((tiles[i][j] - 1) % n - j);
                }
            }
        }
        return ans;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return Arrays.deepEquals(this.tiles, that.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neiBoard = new ArrayList<>();
        int row = 0, col = 0;
        for(int i = 0;i < n;i++) {
            for(int j = 0;j < n;j++) {
                if(tiles[i][j] == 0) {
                    row = i;
                    col = j;
                }
            }
        }
        if (col > 0) {
            neiBoard.add(new Board(neighbor(row, col, row, col - 1)));
        }
        if (row > 0) {
            neiBoard.add(new Board(neighbor(row, col, row - 1, col)));
        }
        if (col < n - 1) {
            neiBoard.add(new Board(neighbor(row, col, row, col + 1)));
        }
        if (row < n - 1) {
            neiBoard.add(new Board(neighbor(row, col, row + 1, col)));
        }
        return neiBoard;
    }

    private int[][] neighbor(int i1, int j1, int i2, int j2) {
        int[][] copy = new int[n][n];
        for(int i = 0;i < n;i++) {
            for(int j = 0;j < n;j++) {
                copy[i][j] = tiles[i][j];
            }
        }
        int temp = copy[i1][j1];
        copy[i1][j1] = copy[i2][j2];
        copy[i2][j2] = temp;
        return copy;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] copy = new int[n][n];
        for(int i = 0;i < n;i++) {
            for(int j = 0;j < n;j++) {
                copy[i][j] = tiles[i][j];
            }
        }
        int j = 0;
        for(int i = 0;i < n-1;i++) {
            if(copy[j][i] != 0 && copy[j][i+1] != 0) {
                return new Board(neighbor(j,i,j,i+1));
            }
        }
        return new Board(neighbor(1,0,1,1));
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}