package geometry;

/**
 * Class of a Point.
 */
public class Point {

    private double x;
    private double y;

    private static final double THRESHOLD = 0.000001;

    /**
     * Constructs a new Point.
     *
     * @param x the x cordinate of the point
     * @param y the y cordinate of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calculates the distance between this point and another point.
     *
     * @param other the other point to check the distance to
     * @return the distance between this point and the other point
     */
    public double distance(Point other) {
        if (other == null) {
            return -1;
        }
        return Math.sqrt(((x - other.x) * (x - other.x)) + ((y - other.y) * (y - other.y)));
    }

    /**
     * Checks if this point is equal to another point.
     * @param other the point to compare
     * @return true if the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        if (other == null) {
            return false;
        }
        return Math.abs(this.x - other.x) < THRESHOLD && Math.abs(this.y - other.y) < THRESHOLD;
    }

    /**
     * Returns the x cordinate of the point.
     *
     * @return the x cordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y cordinate of the point.
     *
     * @return the y cordinate
     */
    public double getY() {
        return y;
    }
}

