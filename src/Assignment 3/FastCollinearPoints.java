import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.IllegalArgumentException;

public class FastCollinearPoints {

    private LineSegment[] segments;


    public FastCollinearPoints(Point[] points) {
        if(points == null) {
            throw new IllegalArgumentException();
        }
        for(int i = 0;i < points.length;i++) {
            if(points[i] == null){
                throw new IllegalArgumentException();
            }
        }
        Point[] copy1 = Arrays.copyOf(points,points.length);
        Arrays.sort(copy1);
        for(int i = 0;i < copy1.length-1;i++) {
            if(copy1[i].compareTo(copy1[i+1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
        Point[] copy2 = Arrays.copyOf(points,points.length);
        List<LineSegment> lineSegments = new ArrayList<>();
        for (int i = 0; i < copy1.length; i++) {
            Point base = copy1[i]; //base p
            Arrays.sort(copy2, base.slopeOrder());
            int count = 1;
            Point startPoint = null; //starting point of line
            for (int j = 0; j < copy2.length-1; j++) { //j < copy2.length - 1?
                if (copy2[j].slopeTo(base) == copy2[j + 1].slopeTo(base)) {
                    count++;
                    if (count == 2) {
                        startPoint = copy2[j];
                        count++;
                    }
                    else if (count >= 4 && j + 1 == copy2.length - 1) {
                        if (startPoint.compareTo(base) > 0) {
                            lineSegments.add(new LineSegment(base, copy2[j + 1]));
                        }
                        count = 1;
                    }
                }
                else if (count >= 4) {
                    if (startPoint.compareTo(base) > 0) {
                        lineSegments.add(new LineSegment(base, copy2[j]));
                    }
                    count = 1;
                }
                else {
                    count = 1;
                }
            }
        }
        segments = lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, numberOfSegments());
    }
}
