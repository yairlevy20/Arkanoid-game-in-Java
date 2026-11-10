package geometry;

/**
 * Class of a Velocity.
 * Velocity specifies the change in position on the `x` and the `y` axes.
 */
public class Velocity {

    private double dx;
    private double dy;

    /**
     * Constructor.
     *
     * @param angle the direction of the velocity.
     * @param speed the speed of the velocity.
     * @return a new Velocity instance.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {

        // Convert degrees to radians
        double radians = Math.toRadians(angle);

        // Calculate dx and dy
        double dx = Math.sin(radians) * speed;
        double dy = -Math.cos(radians) * speed;

        return new Velocity(dx, dy);
    }

    /**
     * Constructor.
     *
     * @param dx the change in position on the x.
     * @param dy the change in position on the y.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Gets the change in the x.
     *
     * @return the dx value.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Gets the change in the y.
     *
     * @return the dy value.
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * returns a new point with velocity added.
     *
     * @param p the point.
     * @return new Point after the velocity is added.
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }
}

