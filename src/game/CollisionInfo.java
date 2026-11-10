package game;

import geometry.Point;
import collidables.Collidable;

/**
 * The CollisionInfo class.
 * holds the point at which the collision occurs the collidable object involved in the collision.
 */
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * Constructor.
     * @param collisionPoint the point where the collision occurs.
     * @param collisionObject the collidable object.
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * @return the collidable object.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}

