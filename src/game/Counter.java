package game;

/**
 * The Counter class.
 * Used for counting things.
 */
public class Counter {
    private int count;

    /**
     * Constructor.
     */
    public Counter() {
        this.count = 0;
    }

    /**
     * Add number to current count.
     * @param number the number to add.
     */
    public void increase(int number) {
        this.count += number;
    }

    /**
     * Subtract number from current count.
     * @param number the number to subtract.
     */
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * Get current count.
     * @return the current count.
     */
    public int getValue() {
        return this.count;
    }
}

