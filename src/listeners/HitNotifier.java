package listeners;

/**
 * The HitNotifier interface.
 * Objects that implement this interface sends notification when they are being hit.
 */
public interface HitNotifier {
    /**
     * Add HitListener as a listener to hit events.
     * @param listener the listener.
     */
    void addHitListener(HitListener listener);

    /**
     * Remove HitListener from the list of listeners to hit events.
     * @param listener the listener.
     */
    void removeHitListener(HitListener listener);
}

