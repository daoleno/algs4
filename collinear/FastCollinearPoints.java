import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

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
                if (points[j] == null || points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }

        List<LineSegment> temp = new ArrayList<LineSegment>();
        List<Point> collinearPoints = new ArrayList<Point>();
        Point[] mutablePoints = points.clone();
        Point[] tempPoints = points.clone();
        // sort points
        Arrays.sort(mutablePoints);
        for (int pos = 0; pos < mutablePoints.length; pos++) {
            Point p = mutablePoints[pos];

            // only sort points after pos
            Arrays.sort(tempPoints, pos, mutablePoints.length, p.slopeOrder());

            // reset collinearPoints
            collinearPoints.clear();
            collinearPoints.add(p);

            // all points' slope will compare with curSlope
            double curSlope = p.slopeTo(tempPoints[0]);
            for (int i = 1; i < tempPoints.length; i++) {
                if (p.slopeTo(tempPoints[i]) == curSlope) {
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
                curSlope = p.slopeTo(tempPoints[i]);
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
        return this.segments.clone();

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