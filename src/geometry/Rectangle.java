package geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * The Rectangle class.
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;

    /**
     * Constructor.
     *
     * @param upperLeft the upperLeft point of the rectangle.
     * @param width     the width of the rectangle.
     * @param height    the height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns list of intersection points with the line.
     *
     * @param line the line to check for any intersections.
     * @return a list of intersection points.
     */
    public java.util.List<Point> intersectionPoints(Line line) {

        List<Point> intersectionPoints = new ArrayList<>();

        // get the four boundaries of the rectangle
        Line top = new Line(upperLeft.getX(), upperLeft.getY(),
                            upperLeft.getX() + width, upperLeft.getY());
        Line bottom = new Line(upperLeft.getX(), upperLeft.getY() + height,
                               upperLeft.getX() + width, upperLeft.getY() + height);
        Line left = new Line(upperLeft.getX(), upperLeft.getY(),
                             upperLeft.getX(), upperLeft.getY() + height);
        Line right = new Line(upperLeft.getX() + width, upperLeft.getY(),
                              upperLeft.getX() + width, upperLeft.getY() + height);

        Line[] boundaries = {top, bottom, left, right};

        for (Line edge : boundaries) {
            Point intersection = line.intersectionWith(edge);
            if (intersection != null) {
                intersectionPoints.add(intersection);
            }
        }

        return intersectionPoints;
    }

    /**
     * Returns the width of the rectangle.
     *
     * @return the width.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the rectangle.
     *
     * @return the height.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Returns the upperLeft point of the rectangle.
     *
     * @return the upperLeft point.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }
}

