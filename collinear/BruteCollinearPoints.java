import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null || points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
        Point[] tempPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            tempPoints[i] = points[i];
        }
        Arrays.sort(tempPoints);
        List<LineSegment> temp = new ArrayList<LineSegment>();

        for (int i = 0; i < tempPoints.length; i++) {
            for (int j = i + 1; j < tempPoints.length; j++) {
                for (int k = j + 1; k < tempPoints.length; k++) {
                    if (tempPoints[i].slopeTo(tempPoints[j]) != tempPoints[i].slopeTo(tempPoints[k])) {
                        continue;
                    }
                    for (int l = k + 1; l < tempPoints.length; l++) {
                        if (tempPoints[i].slopeTo(tempPoints[j]) != tempPoints[i].slopeTo(tempPoints[l])) {
                            continue;
                        }
                        temp.add(new LineSegment(tempPoints[i], tempPoints[l]));
                    }
                }
            }
        }
        this.segments = new LineSegment[temp.size()];
        temp.toArray(this.segments);
    }

    // the number of line segments
    public int numberOfSegments() {
        return this.segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return this.segments;
    }

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
