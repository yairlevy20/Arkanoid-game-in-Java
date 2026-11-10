package sprites;

import biuoop.DrawSurface;
import geometry.Point;
import geometry.Line;
import geometry.Velocity;
import collidables.Collidable;
import game.GameEnvironment;
import game.CollisionInfo;
import game.Game;

/**
 * The Ball class.
 */
public class Ball implements Sprite {

    private static final double EPSILON = 0.001;
    private static final double OUTSIDE_OFFSET = 1.0;

    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity velocity;
    private GameEnvironment gameEnvironment;

    /**
     * Constructor for the Ball.
     * @param center the center point of the ball.
     * @param r the radius.
     * @param color the color.
     * @param gameEnvironment the game environment.
     */
    public Ball(Point center, int r, java.awt.Color color, GameEnvironment gameEnvironment) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.gameEnvironment = gameEnvironment;
    }

    /**
     * @return the x of the center.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * @return the y of the center.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * @return the size of the ball.
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * @return the velocity of the ball.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * @param v the new velocity.
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * @param dx change in x.
     * @param dy change in y.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * @return the color of the ball.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Set the color of the ball.
     * @param color the new color.
     */
    public void setColor(java.awt.Color color) {
        this.color = color;
    }

    /**
     * Moves the ball one step.
     */
    public void moveOneStep() {
        Line trajectory = new Line(this.center, this.velocity.applyToPoint(this.center));
        CollisionInfo info = this.gameEnvironment.getClosestCollision(trajectory);

        if (info == null) {
            this.center = this.velocity.applyToPoint(this.center);
        } else {
            this.center = this.getAlmostHitPoint(info.collisionPoint());
            this.velocity = info.collisionObject().hit(this, info.collisionPoint(), this.velocity);
            this.ensureOutside(info.collisionObject());
        }
    }

    /**
     * Ensures the ball doesn't overlap with a collidable object.
     * @param c the collidable object.
     */
    private void ensureOutside(Collidable c) {
        geometry.Rectangle rect = c.getCollisionRectangle();
        double x = this.center.getX();
        double y = this.center.getY();

        if (x >= rect.getUpperLeft().getX() && x <= rect.getUpperLeft().getX() + rect.getWidth()
            && y >= rect.getUpperLeft().getY() && y <= rect.getUpperLeft().getY() + rect.getHeight()) {
            this.center = new Point(x, rect.getUpperLeft().getY() - OUTSIDE_OFFSET);
        }
    }

    /**
     * Calculates the point slightly before collision.
     * @param collisionPoint the hit point.
     * @return the point slightly moved back.
     */
    private Point getAlmostHitPoint(Point collisionPoint) {
        double x = (this.velocity.getDx() > 0) ? -EPSILON : EPSILON;
        double y = (this.velocity.getDy() > 0) ? -EPSILON : EPSILON;
        return new Point(collisionPoint.getX() + x, collisionPoint.getY() + y);
    }

    /**
     * Draws the ball.
     * @param surface the surface.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.radius);
    }

    /**
     * Notifies the ball that time has passed.
     */
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * Removes the ball from the game.
     * @param game the game object.
     */
    public void removeFromGame(Game game) {
        game.removeSprite(this);
    }

    /**
     * Adds the ball to the game.
     * @param g the game object.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }
}

