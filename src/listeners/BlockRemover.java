package listeners;

import collidables.Block;
import sprites.Ball;
import game.Game;
import game.Counter;

/**
 * BlockRemover is responsible of removing blocks from the game,
 * and keeping count of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private Game game;
    private Counter remainingBlocks;

    /**
     * Constructor.
     * @param game the game object.
     * @param remainingBlocks the counter for remaining blocks.
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * Blocks that are hit should be removed from the game.
     * @param block the block being hit.
     * @param b the ball that hit the block.
     */
    public void hitEvent(Block block, Ball b) {
        block.removeFromGame(this.game);
        block.removeHitListener(this);
        this.remainingBlocks.decrease(1);
    }
}

