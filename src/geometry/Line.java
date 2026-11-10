package geometry;

import java.util.List;

/**
 * The Line class.
 */
public class Line {
    private Point start;
    private Point end;
    private static final double THRESHOLD = 0.000001;

    /**
     * Constructor.
     * @param start start point.
     * @param end end point.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructor.
     * @param x1 x of start.
     * @param y1 y of start.
     * @param x2 x of end.
     * @param y2 y of end.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * @return the start point of the line.
     */
    public Point start() {
        return this.start;
    }

    /**
     * @return the end point of the line.
     */
    public Point end() {
        return this.end;
    }

    /**
     * Calculates the intersection point between this line and another.
     * @param other other line.
     * @return the intersection point, or null if the lines don't intersect.
     */
    public Point intersectionWith(Line other) {
        if (other == null) {
            return null;
        }

        double x1 = this.start.getX(), y1 = this.start.getY();
        double x2 = this.end.getX(), y2 = this.end.getY();
        double x3 = other.start.getX(), y3 = other.start.getY();
        double x4 = other.end.getX(), y4 = other.end.getY();

        double denominator = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
        if (Math.abs(denominator) < THRESHOLD) {
            return null;
        }

        double t1 = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / denominator;
        double t2 = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / denominator;

        if (t1 >= -THRESHOLD && t1 <= 1 + THRESHOLD && t2 >= -THRESHOLD && t2 <= 1 + THRESHOLD) {
            return new Point(x1 + t1 * (x2 - x1), y1 + t1 * (y2 - y1));
        }
        return null;
    }

    /**
     * If this line doesn't intersect with the rectangle - return null.
     * Otherwise, return the closest intersection point from the start of the line.
     * @param rect the rectangle to check.
     * @return the closest intersection point or null.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {

        List<Point> intersections = rect.intersectionPoints(this);

        if (intersections.isEmpty()) {
            return null;
        }

        // find the point with minimum distance
        Point closest = intersections.get(0);
        double minDistance = this.start.distance(closest);

        for (int i = 1; i < intersections.size(); i++) {
            Point currentPoint = intersections.get(i);
            double currentDist = this.start.distance(currentPoint);

            if (currentDist < minDistance) {
                minDistance = currentDist;
                closest = currentPoint;
            }
        }

        return closest;
    }
}

