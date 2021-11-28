import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.IllegalArgumentException;

public class BruteCollinearPoints {
    private LineSegment[] segments;
    private int numOfSegments;

    public BruteCollinearPoints(Point[] points) {
        //exception
        if(points == null) {
            throw new IllegalArgumentException();
        }
        Point[] copy = Arrays.copyOf(points,points.length);
        for(int i = 0;i < points.length;i++) {
            if(points[i] == null){
                throw new IllegalArgumentException();
            }
        }
        Arrays.sort(copy);
        for(int i = 0;i < copy.length-1;i++) {
            if(copy[i].compareTo(copy[i+1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
        List<LineSegment> lineSegments = new ArrayList<>();
        for(int i = 0;i < copy.length;i++) {
            for(int j = i + 1;j < copy.length;j++) {
                for(int k = j + 1;k < copy.length;k++) {
                    for(int l = k + 1;l < copy.length;l++) {
                        if(copy[i].slopeTo(copy[j]) == copy[i].slopeTo(copy[k])
                        && copy[i].slopeTo(copy[j]) == copy[i].slopeTo(copy[l])) {
                            lineSegments.add(new LineSegment(copy[i], copy[l]));
                        }
                    }
                }
            }
        }
        numOfSegments = lineSegments.size();
        segments = lineSegments.toArray(new LineSegment[numOfSegments]);
    }

    public int numberOfSegments() {
        return numOfSegments;
    }
    public LineSegment[] segments() {
        return Arrays.copyOf(segments,numberOfSegments());
    }
}