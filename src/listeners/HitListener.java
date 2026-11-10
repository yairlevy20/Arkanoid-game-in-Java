package listeners;

import collidables.Block;
import sprites.Ball;

/**
 * The HitListener interface.
 * Objects that implement this interface can register as listeners for hit events.
 */
public interface HitListener {
    /**
     * This method is called whenever the block object is hit.
     * @param block the block being hit.
     * @param b the Ball that hit the block.
     */
    void hitEvent(Block block, Ball b);
}

