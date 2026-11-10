package listeners;

import collidables.Block;
import sprites.Ball;
import game.Counter;

/**
 * ScoreTrackingListener is responsible of updating the score.
 */
public class ScoreTrackingListener implements HitListener {

    private static final int POINTS_PER_HIT = 5;
    private Counter currentScore;

    /**
     * Constructor.
     * @param scoreCounter the counter for current score.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * Add points when a block is hit and removed.
     * @param block the block being hit.
     * @param b the ball that hit the block.
     */
    public void hitEvent(Block block, Ball b) {
        this.currentScore.increase(POINTS_PER_HIT);
    }
}

