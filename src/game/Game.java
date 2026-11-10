package game;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;
import sprites.Ball;
import sprites.Sprite;
import sprites.SpriteCollection;
import collidables.Collidable;
import collidables.Block;
import collidables.Paddle;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import listeners.BlockRemover;
import listeners.BallRemover;
import listeners.ScoreTrackingListener;
import ui.ScoreIndicator;

/**
 * The Game class.
 * Holds the sprites and the collidables, and will be in charge of the animation.
 */
public class Game {
    // Screen dimensions and configurations
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final int BORDER_THICKNESS = 20;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECONDS_PER_FRAME = 1000 / FRAMES_PER_SECOND;

    // Paddle dimensions and spawn position
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_START_X = 400;
    private static final int PADDLE_START_Y = 560;

    // Ball configurations
    private static final int BALL_RADIUS = 5;
    private static final double BALL_SPEED = 4.0;
    private static final int BALL1_START_X = 500;
    private static final int BALL1_START_Y = 300;
    private static final int BALL1_ANGLE = 45;
    private static final int BALL2_START_X = 400;
    private static final int BALL2_START_Y = 400;
    private static final int BALL2_ANGLE = -45;
    private static final int BALL3_X_OFFSET = 50;
    private static final int BALL3_ANGLE_OFFSET = 30;

    private static final int NUM_BALLS = 3;

    // Grid layout for bricks
    private static final int BLOCK_WIDTH = 50;
    private static final int BLOCK_HEIGHT = 20;
    private static final int BLOCKS_START_Y = 100;
    private static final int NUM_BLOCK_ROWS = 6;
    private static final int MAX_BLOCKS_PER_ROW = 12;

    private static final int LEVEL_CLEAR_BONUS = 100;

    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Sleeper sleeper;
    private Counter remainingBlocks;
    private Counter remainingBalls;
    private Counter score;

    /**
     * Constructor.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.remainingBlocks = new Counter();
        this.remainingBalls = new Counter();
        this.score = new Counter();
    }

    /**
     * Adds a collidable object to the game environment.
     * @param c the collidable to add.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Remove a collidable object from the game environment.
     * @param c the collidable to remove.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Adds a sprite to the sprite collection.
     * @param s the sprite to add.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Remove a sprite from the sprite collection.
     * @param s the sprite to remove.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Initialize the game.
     */
    public void initialize() {
        this.gui = new GUI("Arkanoid", SCREEN_WIDTH, SCREEN_HEIGHT);
        this.sleeper = new Sleeper();
        biuoop.KeyboardSensor keyboard = gui.getKeyboardSensor();

        // Create the paddle
        Rectangle rectangle = new Rectangle(new Point(PADDLE_START_X, PADDLE_START_Y), PADDLE_WIDTH, PADDLE_HEIGHT);
        Paddle paddle = new Paddle(rectangle, Color.ORANGE, keyboard, SCREEN_WIDTH);
        paddle.addToGame(this);

        // Create balls
        Ball ball1 = new Ball(new Point(BALL1_START_X, BALL1_START_Y), BALL_RADIUS, Color.WHITE, this.environment);
        ball1.setVelocity(Velocity.fromAngleAndSpeed(BALL1_ANGLE, BALL_SPEED));
        ball1.addToGame(this);

        Ball ball2 = new Ball(new Point(BALL2_START_X, BALL2_START_Y), BALL_RADIUS, Color.WHITE, this.environment);
        ball2.setVelocity(Velocity.fromAngleAndSpeed(BALL2_ANGLE, BALL_SPEED));
        ball2.addToGame(this);

        Ball ball3 = new Ball(new Point(BALL1_START_X - BALL3_X_OFFSET, BALL1_START_Y),
                              BALL_RADIUS, Color.WHITE, this.environment);
        ball3.setVelocity(Velocity.fromAngleAndSpeed(BALL1_ANGLE + BALL3_ANGLE_OFFSET, BALL_SPEED));
        ball3.addToGame(this);

        this.remainingBalls.increase(NUM_BALLS);

        // Create the walls
        Rectangle topWall = new Rectangle(
                new Point(0, 0), SCREEN_WIDTH, BORDER_THICKNESS);
        Block topWallBlock = new Block(topWall, Color.GRAY);
        topWallBlock.setWall(true);
        topWallBlock.addToGame(this);

        Rectangle leftWall = new Rectangle(
                new Point(0, BORDER_THICKNESS), BORDER_THICKNESS, SCREEN_HEIGHT - BORDER_THICKNESS);
        Block leftWallBlock = new Block(leftWall, Color.GRAY);
        leftWallBlock.setWall(true);
        leftWallBlock.addToGame(this);

        Rectangle rightWall = new Rectangle(
                new Point(SCREEN_WIDTH - BORDER_THICKNESS, BORDER_THICKNESS),
                BORDER_THICKNESS, SCREEN_HEIGHT - BORDER_THICKNESS);
        Block rightWallBlock = new Block(rightWall, Color.GRAY);
        rightWallBlock.setWall(true);
        rightWallBlock.addToGame(this);


        // Create death region at the bottom
        Rectangle deathRect = new Rectangle(
                new Point(0, SCREEN_HEIGHT), SCREEN_WIDTH, BORDER_THICKNESS);
        Block deathRegion = new Block(deathRect, Color.BLACK);
        deathRegion.addToGame(this);
        BallRemover ballRemover = new BallRemover(this, this.remainingBalls);
        deathRegion.addHitListener(ballRemover);

        // Create the blocks
        Color[] colors = {Color.GRAY, Color.RED, Color.YELLOW, Color.BLUE, Color.PINK, Color.GREEN};
        BlockRemover blockRemover = new BlockRemover(this, this.remainingBlocks);
        ScoreTrackingListener scoreTracker = new ScoreTrackingListener(this.score);

        for (int i = 0; i < NUM_BLOCK_ROWS; i++) {
            for (int j = 0; j < (MAX_BLOCKS_PER_ROW - i); j++) {
                int currentX = SCREEN_WIDTH - BORDER_THICKNESS - ((j + 1) * BLOCK_WIDTH);
                int currentY = BLOCKS_START_Y + (i * BLOCK_HEIGHT);
                Point p = new Point(currentX, currentY);
                Block block = new Block(new Rectangle(p, BLOCK_WIDTH, BLOCK_HEIGHT), colors[i]);
                block.addToGame(this);
                block.addHitListener(blockRemover);
                block.addHitListener(scoreTracker);
                this.remainingBlocks.increase(1);
            }
        }

        // Add score indicator
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score);
        scoreIndicator.addToGame(this);
    }

    /**
     * Run the game.
     */
    public void run() {
        boolean playerWon = false;
        while (remainingBalls.getValue() > 0 && remainingBlocks.getValue() > 0) {
            long startTime = System.currentTimeMillis(); // timing

            DrawSurface d = gui.getDrawSurface();
            d.setColor(Color.BLACK);
            d.fillRectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();

            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = MILLISECONDS_PER_FRAME - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }

        // Determine outcome
        if (remainingBlocks.getValue() == 0) {
            playerWon = true;
            this.score.increase(LEVEL_CLEAR_BONUS);
        }

        // Print final message
        if (playerWon) {
            System.out.println("You Win!\nYour score is: " + this.score.getValue());
        } else {
            System.out.println("Game Over.\nYour score is: " + this.score.getValue());
        }

        this.gui.close();
    }
}

