import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    private Picture pic;
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if(picture == null) {
            throw new IllegalArgumentException();
        }
        pic = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return new Picture(pic);
    }

    // width of current picture
    public int width() {
        return pic.width();
    }

    // height of current picture
    public int height() {
        return pic.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height()) {
            throw new IllegalArgumentException();
        } else if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) {
            return 1000;
        }
        return Math.sqrt(gradient(x + 1, y, x - 1, y)
                + gradient(x, y + 1, x, y - 1));
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        double[][] energy = EMatrix();
        for (int i = width() - 2; i >= 0; i--) {
            for (int j = 0; j < height(); j++) {
                double minE = energy[j][i + 1];
                if (j > 0) {
                    if(minE > energy[j - 1][i + 1]){
                        minE = energy[j - 1][i + 1];
                    }
                }
                if (j < height() - 1) {
                    if(minE > energy[j+1][i+1]) {
                        minE = energy[j+1][i+1];
                    }
                }
                energy[j][i] += minE;
            }
        }

        int[] horizontalSeam = new int[width()];
        double minE = Double.MAX_VALUE;
        int start = 0;
        for (int j = 0; j < height(); j++) {
            if (minE > energy[j][0]) {
                minE = energy[j][0];
                start = j;
            }
        }

        horizontalSeam[0] = start;
        for (int i = 1; i < width(); i++) {
            double minn = energy[start][i];
            if (start > 0) minn = Math.min(minn, energy[start-1][i]);
            if (start < height() - 1) minn = Math.min(minn, energy[start+1][i]);
            if (start > 0 && minn == energy[start - 1][i]) {
                start--;
            } else if (start < height() - 1 &&  minn == energy[start+1][i]) {
                start++;
            }
            horizontalSeam[i] = start;
        }

        return horizontalSeam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] energy = EMatrix();
        for (int j = height() - 2; j >= 0; j--) {
            for (int i = 0; i < width(); i++) {
                double minE = energy[j + 1][i];
                if (i > 0) {
                    if (minE > energy[j+1][i-1]){
                        minE = energy[j+1][i-1];
                    }
                }
                if (i < width()-1) {
                    if (minE > energy[j+1][i+1]){
                        minE = energy[j+1][i+1];
                    }
                }
                energy[j][i] += minE;
            }
        }

        int[] verticalSeam = new int[height()];
        double minE = Double.MAX_VALUE;
        int start = 0;
        for (int i = 0; i < width(); i++) {
            if (minE > energy[0][i]) {
                minE = energy[0][i];
                start = i;
            }
        }

        verticalSeam[0] = start;
        for (int j = 1; j < height(); j++) {
            double min = energy[j][start];
            if (start > 0) {
                if(min > energy[j][start-1]) {
                    min = energy[j][start-1];
                }
            }
            if (start < width() - 1) {
                if(min > energy[j][start+1]){
                    min = energy[j][start+1];
                }
            }
            if (start > 0 && min == energy[j][start-1]) {
                start--;
            } else if (start < width() - 1 && min == energy[j][start + 1]) {
                start++;
            }
            verticalSeam[j] = start;
        }

        return verticalSeam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || seam.length != width()) throw new IllegalArgumentException();
        if (seam[0] < 0 || seam[0] > height() - 1) throw new IllegalArgumentException();
        for (int i = 1; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > height() - 1) throw new IllegalArgumentException();
            if ((seam[i] - seam[i - 1]) * (seam[i] - seam[i - 1]) > 1) throw new IllegalArgumentException();
        }
        int i = 0;
        Picture newPic = new Picture(width(), height() - 1);
        for (int j : seam) {
            for (int k = 0; k < newPic.height(); k++) {
                if (k < j) newPic.set(i, k, pic.get(i, k));
                else newPic.set(i, k, pic.get(i, k + 1));
            }
            i++;
        }
        pic = newPic;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || seam.length != height()) {
            throw new IllegalArgumentException();
        }
        if (seam[0] < 0 || seam[0] > width()-1) {
            throw new IllegalArgumentException();
        }
        for (int i = 1; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > width()-1) {
                throw new IllegalArgumentException();
            }
            if ((seam[i] - seam[i-1]) * (seam[i] - seam[i-1]) > 1) {
                throw new IllegalArgumentException();
            }
        }
        int j = 0;
        Picture newPic = new Picture(width()-1, height());
        for (int i: seam) {
            for (int k = 0; k < newPic.width(); k++) {
                if (k < i) newPic.set(k, j, pic.get(k, j));
                else newPic.set(k, j, pic.get(k+1, j));
            }
            j++;
        }
        pic = newPic;
    }

    //  unit testing (optional)
    public static void main(String[] args) {

    }

    private int gradient(int xA, int yA, int xB, int yB) {
        Color color1 = pic.get(xA, yA);
        Color color2 = pic.get(xB, yB);
        return (int) (Math.pow(color1.getRed() - color2.getRed(),2)
                + Math.pow(color1.getGreen() - color2.getGreen(), 2)
                + Math.pow(color1.getBlue() - color2.getBlue(), 2));
    }

    private double[][] EMatrix() {
        double[][] a = new double[height()][width()];
        for (int j = 0; j < height(); j++) {
            for (int i = 0; i < width(); i++) {
                a[j][i] = energy(i, j);
            }
        }
        return a;
    }
}