package src;

/**
 * Represents a chopstick in the Dining Philosophers problem.
 * Each chopstick can be acquired or released by a philosopher,
 * and tracks whether it is currently in use.
 */
public class Chopstick {
    private boolean inUse;

    /**
     * Constructs a new chopstick that is not in use initially.
     */
    public Chopstick() {
        this.inUse = false;
    }

    /**
     * Attempts to acquire the chopstick.
     * If the chopstick is not in use, it will be marked as in use and
     * the method returns true. If the chopstick is already in use,
     * the method returns false.
     *
     * @return true if the chopstick was successfully acquired, false otherwise.
     */
    public synchronized boolean aquire() {
        if (inUse){
            return false;
        }
        inUse = true;
        return true;
    }

    /**
     * Releases the chopstick, marking it as no longer in use.
     * If the chopstick is not currently in use, the method returns false.
     *
     * @return true if the chopstick was successfully released, false otherwise.
     */
    public synchronized boolean release() {
        if (!inUse){
            return false;
        }
        inUse = false;
        return true;
    }

    /**
     * Checks whether the chopstick is currently in use.
     *
     * @return true if the chopstick is in use, false otherwise.
     */
    public synchronized boolean isInUse() {
        return inUse;
    }
}
