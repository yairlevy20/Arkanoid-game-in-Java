package collidables;

import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import sprites.Ball;

/**
 * The Collidable interface.
 * Used by objects that can collide with things
 */
public interface Collidable {

    /**
     * Return the "collision shape" of the object.
     * @return the collision rectangle.
     */
    Rectangle getCollisionRectangle();

    /**
     *  Notify the object that we collided with it at collisionPoint with
     *  a given velocity.
     *  The return is the new velocity expected after the hit (based on
     *  the force the object inflicted on us).
     * @param ball the ball hitting this collidable.
     * @param collisionPoint the point where the collision occurred.
     * @param currentVelocity the velocity of the object hitting the collidable.
     * @return the new velocity after the collision.
     */
    Velocity hit(Ball ball, Point collisionPoint, Velocity currentVelocity);
}

