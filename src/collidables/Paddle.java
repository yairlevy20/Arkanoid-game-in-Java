package collidables;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import sprites.Ball;
import sprites.Sprite;
import game.Game;

/**
 * The Paddle class (the player).
 */
public class Paddle implements Sprite, Collidable {
    private static final int DEFAULT_STEP = 7;
    private static final int PADDLE_ZONES = 5;
    private static final double ZONE_1_ANGLE = 300;
    private static final double ZONE_2_ANGLE = 330;
    private static final double ZONE_4_ANGLE = 30;
    private static final double ZONE_5_ANGLE = 60;
    private static final int ZONE_2_MULTIPLIER = 2;
    private static final int ZONE_3_MULTIPLIER = 3;
    private static final int ZONE_4_MULTIPLIER = 4;

    private KeyboardSensor keyboard;
    private Rectangle rect;
    private Color color;
    private int screenWidth;
    private int step = DEFAULT_STEP;
    private Block secondaryBlock; // secondary collision area for the paddle
    private Game game; // to add/remove the secondary paddle

    /**
     * Constructor.
     * @param rect        the rectangle paddle shape.
     * @param color       the color of the paddle.
     * @param k           the keyboard sensor for movement.
     * @param screenWidth the width of the game window.
     */
    public Paddle(Rectangle rect, Color color, KeyboardSensor k, int screenWidth) {
        this.rect = rect;
        this.color = color;
        this.keyboard = k;
        this.screenWidth = screenWidth;
    }

    /**
     * Update the secondary block paddle for collisions.
     * If the paddle is partially off the screen, a temporary collidable block
     * is added to the other side so the ball can also bounce on it.
     */
    private void updateSecondaryBlock() {
        if (this.game == null) {
            return;
        }

        double x = rect.getUpperLeft().getX();
        double w = rect.getWidth();
        double h = rect.getHeight();
        double y = rect.getUpperLeft().getY();

        // Check if paddle is currently crossing an edge
        if (x + w > screenWidth || x < 0) {
            double secondaryBlockX = (x < 0) ? x + screenWidth : x - screenWidth;
            Rectangle secondaryRect = new Rectangle(new Point(secondaryBlockX, y), w, h);

            // update the secondaryBlock
            if (secondaryBlock != null) {
                this.game.removeCollidable(secondaryBlock);
            }
            secondaryBlock = new Block(secondaryRect, color);
            this.game.addCollidable(secondaryBlock);

        } else if (secondaryBlock != null) {
            // Remove secondaryBlock if paddle is fully back on screen
            this.game.removeCollidable(secondaryBlock);
            secondaryBlock = null;
        }
    }

    /**
     * Move the paddle to the left. Wraps around if it moves fully off the screen.
     */
    public void moveLeft() {
        double x = rect.getUpperLeft().getX() - step;
        if (x < -rect.getWidth()) {
            x += screenWidth;
        }
        this.rect = new Rectangle(new Point(x, rect.getUpperLeft().getY()),
                                  rect.getWidth(), rect.getHeight());
        updateSecondaryBlock();
    }

    /**
     * Move the paddle to the right. Wraps around if it moves fully off the screen.
     */
    public void moveRight() {
        double x = rect.getUpperLeft().getX() + step;
        if (x > screenWidth) {
            x -= screenWidth;
        }
        this.rect = new Rectangle(new Point(x, rect.getUpperLeft().getY()),
                                  rect.getWidth(), rect.getHeight());
        updateSecondaryBlock();
    }

    /**
     * Checks for keyboard input and move the paddle accordingly.
     */
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    /**
     * Draws the paddle.
     * If it is crossing the screen edge it will draws a second rect on the other side.
     * @param d the surface to draw on.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        double x = rect.getUpperLeft().getX();
        double y = rect.getUpperLeft().getY();
        double w = rect.getWidth();
        double h = rect.getHeight();

        d.fillRectangle((int) x, (int) y, (int) w, (int) h);

        if (x + w > screenWidth) {
            d.fillRectangle((int) (x - screenWidth), (int) y, (int) w, (int) h);
        } else if (x < 0) {
            d.fillRectangle((int) (x + screenWidth), (int) y, (int) w, (int) h);
        }
    }

    /**
     * Returns the collision shape of the paddle.
     * @return the rectangle.
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * Handles collision logic. Divides paddle into 5 zones for bounce angles.
     * @param ball the ball hitting the paddle.
     * @param collisionPoint  the point where the ball hit the paddle.
     * @param currentVelocity the current velocity of the ball.
     * @return the new velocity after the hit.
     */
    public Velocity hit(Ball ball, Point collisionPoint, Velocity currentVelocity) {
        double speed = Math.sqrt(currentVelocity.getDx() * currentVelocity.getDx()
                       + currentVelocity.getDy() * currentVelocity.getDy());
        double relativeHit = collisionPoint.getX() - rect.getUpperLeft().getX();
        double regionWidth = rect.getWidth() / (double) PADDLE_ZONES;

        if (relativeHit < regionWidth) {
            return Velocity.fromAngleAndSpeed(ZONE_1_ANGLE, speed);
        } else if (relativeHit < ZONE_2_MULTIPLIER * regionWidth) {
            return Velocity.fromAngleAndSpeed(ZONE_2_ANGLE, speed);
        } else if (relativeHit < ZONE_3_MULTIPLIER * regionWidth) {
            return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        } else if (relativeHit < ZONE_4_MULTIPLIER * regionWidth) {
            return Velocity.fromAngleAndSpeed(ZONE_4_ANGLE, speed);
        } else {
            return Velocity.fromAngleAndSpeed(ZONE_5_ANGLE, speed);
        }
    }

    /**
     * Add the paddle and its collision logic to the game.
     * @param g the game instance.
     */
    public void addToGame(Game g) {
        this.game = g;
        g.addSprite(this);
        g.addCollidable(this);
    }
}

