package ui;

import biuoop.DrawSurface;
import java.awt.Color;
import sprites.Sprite;
import game.Counter;
import game.Game;

/**
 * ScoreIndicator is a sprite that displays the current score.
 */
public class ScoreIndicator implements Sprite {

    private static final int TEXT_SIZE = 15;
    private static final int POSITION_X = 350;
    private static final int POSITION_Y = 15;
    private Counter score;

    /**
     * Constructor.
     * @param score the score counter.
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    /**
     * Draw the score on the game screen.
     * @param d the surface.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.WHITE);
        d.drawText(POSITION_X, POSITION_Y, "Score: " + this.score.getValue(), TEXT_SIZE);
    }

    /**
     * Notify that time is passed.
     */
    public void timePassed() {
        // Do nothing.
    }

    /**
     * Add the score indicator to the game.
     * @param g the game object.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }
}

