package sprites;

import biuoop.DrawSurface;
import java.util.ArrayList;
import java.util.List;

/**
 * The SpriteCollection holds list of the sprites.
 */
public class SpriteCollection {

    private List<Sprite> sprites;

    /** Constructor. */
    public SpriteCollection() {

        this.sprites = new ArrayList<>();
    }

    /**
     * Adds a sprite to the list.
     * @param s sprite.
     */
    public void addSprite(Sprite s) {

        this.sprites.add(s);
    }

    /**
     * Remove a sprite from the list.
     * @param s sprite.
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }

    /** call timePassed for all sprites in the list. */
    public void notifyAllTimePassed() {
        // Make a copy to avoid issues when sprites are removed during iteration
        List<Sprite> spritesCopy = new ArrayList<>(this.sprites);
        for (Sprite s : spritesCopy) {
            s.timePassed();
        }
    }

    /**
     * call drawOn for all sprites in the list.
     * @param d surface.
     */
    public void drawAllOn(DrawSurface d) {
        // Make a copy to avoid issues when sprites are removed during iteration
        List<Sprite> spritesCopy = new ArrayList<>(this.sprites);
        for (Sprite s : spritesCopy) {
              s.drawOn(d);
        }
    }
}

