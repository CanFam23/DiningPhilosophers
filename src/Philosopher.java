package src;

/**
 * Represents a philosopher in the Dining Philosophers problem.
 * Each philosopher alternates between thinking and eating, acquiring
 * chopsticks when needed. The philosopher's actions are managed by
 * a controller that coordinates their turns.
 */
public class Philosopher implements Runnable {
    /** Amount of time the philosopher will eat for. */
    private static final int EAT_TIME_MS = 100;

    /** Amount of time the philosopher will think for. */
    private static final int THINK_TIME_MS = 100;

    /** Number of philosopher. */
    private final int philNum;

    /** Philosophers left chopstick. */
    private final Chopstick leftStick;

    /** Philosophers right chopstick. */
    private final Chopstick rightStick;

    /** A flag indicating whether the thread should keep running. It is volatile to ensure visibility across threads. */
    private volatile boolean running = false;

    /** Number of times philosopher has eaten. */
    private int timesEaten = 0;

    /** The thread associated with this philosopher, which executes the {@link #run()} method. */
    private final Thread thread;

    /** Controller to manage turns, each philosopher will only eat when the table says it can */
    private final DiningTable table;

    /**
     * Constructs a philosopher with the given number, chopsticks, and controller.
     *
     * @param philNum    the unique identifier for the philosopher.
     * @param leftStick  the chopstick to the left of the philosopher.
     * @param rightStick the chopstick to the right of the philosopher.
     * @param table      the controller that coordinates turns.
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
     * The philosopher waits for their turn eats, and then thinks.
     */
    @Override
    public void run() {
        while (running) {
            // Wait for the philosopher's turn to eat
            table.waitForTurn(philNum);

            // Try to acquire both chopsticks
            synchronized (leftStick) {
                synchronized (rightStick) {
                    eat();

                    table.finishedEating(philNum);

                    think();
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
        // Uncomment the line below to see when each philosopher eats
//        System.out.println("Philosopher " + philNum + " is eating. Total: " + timesEaten);
        sleep(EAT_TIME_MS);
    }

    /**
     * Simulates the philosopher thinking by printing a message and sleeping.
     */
    private void think() {
        // Uncomment the line below to see when each philosopher thinks
//        System.out.println("Philosopher " + philNum + " is thinking.");
        sleep(THINK_TIME_MS);
    }

    /**
     * Causes the current thread to sleep for the given amount of time.
     *
     * @param millis the time to sleep in milliseconds.
     */
    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }
}
