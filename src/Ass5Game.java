import game.Game;

/**
 * Ass5Game is the class that running the game.
 */
public class Ass5Game {
    /**
     * Main.
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}

