import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Selection;

public class FastCollinearPoints {
    private LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null || points[i] == points[j]) {
                    throw new IllegalArgumentException();
                }
            }
        }
        Arrays.sort(points);
        List<LineSegment> temp = new ArrayList<LineSegment>();
        List<Point> collinearPoints = new ArrayList<Point>();
        Point[] tempPoints = points.clone();

        for (Point p : points) {
            Selection.sort(tempPoints, p.slopeOrder());
            collinearPoints.clear();
            collinearPoints.add(p);
            for (int i = 1; i < tempPoints.length; i++) {
                if (p.slopeTo(tempPoints[i]) == p.slopeTo(tempPoints[i - 1])) {
                    if (!collinearPoints.contains(tempPoints[i - 1])) {
                        collinearPoints.add(tempPoints[i - 1]);
                    }
                    collinearPoints.add(tempPoints[i]);
                    continue;
                }
                if (collinearPoints.size() >= 4) {
                    Collections.sort(collinearPoints);
                    if (p == collinearPoints.get(0)) {
                        temp.add(new LineSegment(p, collinearPoints.get(collinearPoints.size() - 1)));
                    }
                }
                collinearPoints.clear();
                collinearPoints.add(p);
            }
            if (collinearPoints.size() >= 4) {
                Collections.sort(collinearPoints);
                if (p == collinearPoints.get(0)) {
                    temp.add(new LineSegment(p, collinearPoints.get(collinearPoints.size() - 1)));
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}