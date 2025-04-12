package src;

/**
 * Represents a philosopher in the Dining Philosophers problem.
 * Each philosopher alternates between thinking and eating, acquiring
 * chopsticks when needed. The philosopher's actions are managed by
 * a controller that coordinates their turns.
 */
public class Philosopher implements Runnable {

    // Philosophers' properties
    private final int philNum;
    private final Chopstick leftStick;
    private final Chopstick rightStick;

    // Philosopher's state and actions
    private volatile boolean running = false;
    private int timesEaten = 0;
    private final Thread thread;

    // Controller to manage turns
    private final DiningTable table;

    /**
     * Constructs a philosopher with the given number, chopsticks, and controller.
     *
     * @param philNum the unique identifier for the philosopher.
     * @param leftStick the chopstick to the left of the philosopher.
     * @param rightStick the chopstick to the right of the philosopher.
     * @param table the controller that coordinates turns.
     */
    public Philosopher(int philNum, Chopstick leftStick, Chopstick rightStick, DiningTable table) {
        this.philNum = philNum;
        this.leftStick = leftStick;
        this.rightStick = rightStick;
        this.thread = new Thread(this, "Philosopher " + philNum);
        this.table = table;
    }

    /**
     * Starts the philosopher's thread, allowing them to begin their actions.
     */
    public void start() {
        running = true;
        thread.start();
    }

    /**
     * Stops the philosopher's thread, halting their actions.
     */
    public void stopRunning() {
        running = false;
        thread.interrupt();
    }

    /**
     * Returns the total number of times the philosopher has eaten.
     *
     * @return the number of times the philosopher has eaten.
     */
    public int getTimesEaten() {
        return timesEaten;
    }

    /**
     * Returns the philosopher's unique number.
     *
     * @return the philosopher's number.
     */
    public int getPhilNum() {
        return philNum;
    }

    /**
     * The philosopher's main activity loop, alternating between thinking and eating.
     * The philosopher waits for their turn, tries to acquire both chopsticks,
     * eats, releases the chopsticks, and then thinks.
     */
    @Override
    public void run() {
        while (running) {
            // Wait for the philosopher's turn to eat
            table.waitForTurn(philNum);

            // Try to acquire both chopsticks
            synchronized (leftStick) {
                synchronized (rightStick) {
                    if (!leftStick.isInUse() && !rightStick.isInUse()) {
                        // Acquire the chopsticks and proceed to eat
                        leftStick.aquire();
                        rightStick.aquire();

                        eat();

                        // Release the chopsticks after eating
                        leftStick.release();
                        rightStick.release();

                        // Notify the controller that the philosopher finished eating
                        table.finishedEating(philNum);

                        think();
                    }
                }
            }
        }
    }

    /**
     * Simulates the philosopher eating. Increments the times eaten counter
     * and prints a message.
     */
    private void eat() {
        timesEaten++;
        System.out.println("Philosopher " + philNum + " is eating. Total: " + timesEaten);
        sleep(100);
    }

    /**
     * Simulates the philosopher thinking by printing a message and sleeping.
     */
    private void think() {
        System.out.println("Philosopher " + philNum + " is thinking.");
        sleep(100);
    }

    /**
     * Causes the current thread to sleep for the given amount of time.
     *
     * @param millis the time to sleep in milliseconds.
     */
    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {}
    }
}
