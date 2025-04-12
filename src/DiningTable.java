package src;

import java.util.*;

/**
 * Simulates the Dining Philosophers problem where philosophers alternate between
 * eating and thinking, sharing chopsticks with each other, and waiting for their turn
 * to eat according to a predefined wave sequence.
 */
public class DiningTable {

    // List of waves where each wave contains philosophers who will eat together
    private final List<List<Integer>> waves = List.of(
            List.of(1, 3),
            List.of(5, 2),
            List.of(4, 1),
            List.of(3, 5),
            List.of(2, 4)
    );

    private int currentWave = 0;           // Index of the current wave
    private int completedThisWave = 0;     // Number of philosophers that have finished eating in the current wave

    /**
     * Main method to simulate the Dining Philosophers problem.
     */
    public static void main(String[] args) {
        final Philosopher[] philosophers = new Philosopher[5];
        final Chopstick[] chopsticks = new Chopstick[5];
        DiningTable diningTable = new DiningTable();

        // Initialize chopsticks
        for (int i = 0; i < 5; i++) {
            chopsticks[i] = new Chopstick();
        }

        // Initialize philosophers with alternating chopstick pairs and controller
        for (int i = 0; i < 5; i++) {
            if (i < 4) {
                if (i % 2 == 0) {
                    philosophers[i] = new Philosopher(i + 1, chopsticks[i + 1], chopsticks[i], diningTable);
                } else {
                    philosophers[i] = new Philosopher(i + 1, chopsticks[i], chopsticks[i + 1], diningTable);
                }
            } else {
                philosophers[i] = new Philosopher(i + 1, chopsticks[i], chopsticks[0], diningTable);
            }
        }

        // Start philosophers' threads
        for (Philosopher p : philosophers) {
            p.start();
        }

        try {
            Thread.sleep(5000); // Run simulation for 5 seconds
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Stop philosophers and display the results
        System.out.println("Time to stop\n");
        for (Philosopher p : philosophers) {
            System.out.println("Philosopher: " + p.getPhilNum() + " ate " + p.getTimesEaten() + " times");
            p.stopRunning();
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
                Thread.currentThread().interrupt();
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
        completedThisWave++;

        if (completedThisWave >= 2) {
            completedThisWave = 0;
            currentWave = (currentWave + 1) % waves.size(); // Move to the next wave
            notifyAll();  // Let next wave proceed
        }
    }
}
