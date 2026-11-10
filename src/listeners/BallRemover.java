package listeners;

import collidables.Block;
import sprites.Ball;
import game.Game;
import game.Counter;

/**
 * BallRemover responsible of removing balls from the game,
 * and keeping count of the number of balls that remain.
 */
public class BallRemover implements HitListener {
    private Game game;
    private Counter remainingBalls;

    /**
     * Constructor.
     * @param game the game object.
     * @param remainingBalls the counter for remaining balls.
     */
    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    /**
     * Balls that hit the death region should be removed.
     * @param block the block being hit (this is the death region).
     * @param b the ball that hit the death region.
     */
    public void hitEvent(Block block, Ball b) {
        b.removeFromGame(this.game);
        this.remainingBalls.decrease(1);
    }
}

