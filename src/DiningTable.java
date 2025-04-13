package src;

import java.util.List;

/**
 * Simulates the Dining Philosophers problem where philosophers alternate between
 * eating and thinking, sharing chopsticks with each other, and waiting for their turn
 * to eat according to a predefined wave sequence.
 */
public class DiningTable {
    /** Amount of time the philosophers will eat for. */
    private static final int DINING_TIME_MS = 5 * 1000;

    /** List of waves where each wave contains philosophers who will eat together. */
    private final List<List<Integer>> waves = List.of(
            List.of(1, 3),
            List.of(5, 2),
            List.of(4, 1),
            List.of(3, 5),
            List.of(2, 4)
    );

    /** Size of waves. */
    private final int waveSize = waves.getFirst().size();

    /** Index of the current wave. */
    private int currentWave = 0;

    /** Number of philosophers that have finished eating in the current wave. */
    private int completedThisWave = 0;

    /**
     * Main method to simulate the Dining Philosophers problem.
     *
     * @param args Arguments passed. Not used.
     */
    public static void main(String[] args) {
        final Philosopher[] philosophers = new Philosopher[5];
        final Chopstick[] chopsticks = new Chopstick[5];
        final DiningTable diningTable = new DiningTable();

        // Initialize chopsticks
        for (int i = 0; i < 5; i++) {
            chopsticks[i] = new Chopstick();
        }

        // Initialize philosophers
        for (int i = 0; i < 5; i++) {
            if (i < 4) {
                philosophers[i] = new Philosopher(i + 1, chopsticks[i], chopsticks[i + 1], diningTable);
            } else {
                philosophers[i] = new Philosopher(i + 1, chopsticks[i], chopsticks[0], diningTable);
            }
        }

        System.out.println("Allowing Philosophers to eat for " + (DINING_TIME_MS / 1000) + " seconds...");

        // Start philosophers' threads
        for (Philosopher p : philosophers) {
            p.start();
        }

        try {
            Thread.sleep(DINING_TIME_MS); // Give philosophers time to eat
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Stop philosophers and display the results
        for (Philosopher p : philosophers) {
            p.stopRunning();
            System.out.println("Philosopher: " + p.getPhilNum() + " ate " + p.getTimesEaten() + " times");
        }
        System.exit(0);
    }

    /**
     * Makes a philosopher wait for their turn to eat based on the current wave.
     * The philosopher can only eat if they are part of the current wave.
     *
     * @param philNum the philosopher's unique number.
     */
    public synchronized void waitForTurn(int philNum) {
        while (!waves.get(currentWave).contains(philNum)) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println("Error: " + Thread.currentThread().getName() + " was interrupted while waiting for it's turn to eat!");
            }
        }
    }

    /**
     * Called when a philosopher has finished eating in a wave. If all philosophers
     * in the wave have finished, move on to the next wave and notify all philosophers.
     *
     * @param philNum the philosopher's unique number.
     */
    public synchronized void finishedEating(int philNum) {
        // Make sure the philosopher finished eating is in the current wave
        if (!waves.get(currentWave).contains(philNum)) {
            System.err.println("Error in finishedEating: Phil " + philNum + " is not in the current wave (" + (currentWave + 1) + "). Aborting program");
            System.exit(1);
        }

        completedThisWave++;

        if (completedThisWave >= waveSize) {
            completedThisWave = 0;
            currentWave = (currentWave + 1) % waves.size(); // Move to the next wave
            notifyAll();  // Let next wave proceed
        }
    }
}
