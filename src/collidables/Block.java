package collidables;

import biuoop.DrawSurface;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import listeners.HitListener;
import listeners.HitNotifier;
import sprites.Ball;
import sprites.Sprite;
import game.Game;

/**
 * The Block class.
 * Blocks are obstacles that appear on the screen
 */
public class Block implements Collidable, Sprite, HitNotifier {

    private static final double EPSILON = 0.001;

    private Rectangle rect;
    private Color color;
    private List<HitListener> hitListeners;
    private boolean isWall;

    /**
     * Constructor.
     * @param rect the rectangle.
     * @param color the color of the block.
     */
    public Block(Rectangle rect, Color color) {
        this.rect = rect;
        this.color = color;
        this.hitListeners = new ArrayList<>();
        this.isWall = false;
    }

    /**
     * Sets whether this block is a wall.
     * @param isWall true if it is a wall, false otherwise.
     */
    public void setWall(boolean isWall) {
        this.isWall = isWall;
    }

    /**
     * Check if this block is a wall.
     * @return true if this is a wall, false otherwise.
     */
    public boolean isWall() {
        return this.isWall;
    }

    /**
     * @return the collision rectangle.
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * Check if the ball color matches this block's color.
     * @param ball the ball to check.
     * @return true if colors match, false otherwise.
     */
    public boolean ballColorMatch(Ball ball) {
        return this.color.equals(ball.getColor());
    }

    /**
     * Notify all listeners that this block has been hit.
     * @param b the ball that hit this block.
     */
    private void notifyHit(Ball b) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener l : listeners) {
            l.hitEvent(this, b);
        }
    }

    /**
     * Add HitListener as a listener to hit events.
     * @param l the hit listener.
     */
    public void addHitListener(HitListener l) {
        this.hitListeners.add(l);
    }

    /**
     * Remove HitListener from the list of listeners to hit events.
     * @param l the hit listener.
     */
    public void removeHitListener(HitListener l) {
        this.hitListeners.remove(l);
    }

    /**
     *  Notify the object that we collided with it at collisionPoint with
     *  a given velocity.
     *  The return is the new velocity expected after the hit (based on
     *  the force the object inflicted on us).
     * @param hitter the ball hitting this collidable.
     * @param collisionPoint the point where the collision occurred.
     * @param currentVelocity the velocity of the object hitting the collidable.
     * @return the new velocity after the collision.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {

        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        double minX = rect.getUpperLeft().getX();
        double maxX = minX + rect.getWidth();
        double minY = rect.getUpperLeft().getY();
        double maxY = minY + rect.getHeight();

        if (Math.abs(collisionPoint.getX() - minX) < EPSILON || Math.abs(collisionPoint.getX() - maxX) < EPSILON) {
            dx = -dx;
        }
        if (Math.abs(collisionPoint.getY() - minY) < EPSILON || Math.abs(collisionPoint.getY() - maxY) < EPSILON) {
            dy = -dy;
        }

        // Only notify listeners and change ball color if:
        // 1. Colors don't match AND
        // 2. This is NOT a wall (gray block)
        if (!ballColorMatch(hitter) && !isWall()) {
            hitter.setColor(this.color);
            this.notifyHit(hitter);
        }

        return new Velocity(dx, dy);
    }

    /**
     * Draws the block.
     * @param d the surface.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                        (int) rect.getWidth(), (int) rect.getHeight());
        d.setColor(Color.BLACK);
        d.drawRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                        (int) rect.getWidth(), (int) rect.getHeight());
    }

    /**
     * timePassed indication.
     */
    public void timePassed() {
        // currently do nothing.
    }

    /**
     * Adds the block to the game.
     * @param g the game.
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * Remove this block from the game.
     * @param game the game.
     */
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }
}

