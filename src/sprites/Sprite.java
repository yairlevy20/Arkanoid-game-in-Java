package sprites;

import biuoop.DrawSurface;

/**
 * The Sprite interface.
 * Sprites can be drawn on the screen and can be notified that time has passed
 * so that they know to change their position/shape/appearance etc.
 */
public interface Sprite {
    /**
     * Draw the sprite.
     * @param d the surface.
     */
    void drawOn(DrawSurface d);

    /**
     * Notify that time is passed.
     */
    void timePassed();
}

