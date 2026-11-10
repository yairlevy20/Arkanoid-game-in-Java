package game;

import java.util.ArrayList;
import java.util.List;
import geometry.Point;
import geometry.Line;
import collidables.Collidable;

/**
 * The GameEnvironment class.
 * A collection of objects a Ball can collide with.
 */
public class GameEnvironment {
    private List<Collidable> collidables;

    /**
     * Constructor.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    /**
     * Add the given collidable to the environment.
     * @param c the collidable to add.
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Remove the given collidable from the environment.
     * @param c the collidable to remove.
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    /**
     * Find the closest collision that is going to occur on the trajectory.
     * @param trajectory the ball's intended path.
     * @return CollisionInfo for the closest hit. null if there is no hit.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        List<Collidable> collidablesCopy = new ArrayList<>(this.collidables);
        Point closestPoint = null;
        Collidable closestObject = null;
        double minDistance = Double.MAX_VALUE;

        for (Collidable c : collidablesCopy) {
            Point collision = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            if (collision != null) {
                double distance = trajectory.start().distance(collision);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPoint = collision;
                    closestObject = c;
                }
            }
        }

        if (closestPoint == null) {
            return null;
        }

        return new CollisionInfo(closestPoint, closestObject);
    }
}

