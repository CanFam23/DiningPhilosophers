package src;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Chopstick {
    /**
     * Some threads gets to eat more then others. TO solve this, implement this queue
     * When a thread wants to acquire, check if they are at the front of the queue (Least times ate)
     * If so, give them access
     * If not, don't
     * Keep track of who currently has access
     */
    private final Queue<Philosopher> threads;

    /** Could be useful for debugging */
    private int chopstickNum;

    private volatile boolean inUse;

    public Chopstick(int chopstickNum) {
        this.chopstickNum = chopstickNum;
        inUse = false;
        threads = new PriorityQueue<>(Comparator.comparingInt(Philosopher::getTimesEaten));
    }

    public synchronized boolean aquire() {
        if(inUse){
//            throw new IllegalStateException("Chopstick already in use");
            return false;
        }

        inUse = true;
        return true;
    }

    public synchronized boolean release(){
        if(!inUse){
            return false;
        }

        inUse = false;
        return true;
    }

    public boolean isInUse() {
        return inUse;
    }

    @Override
    public String toString() {
        return "Chopstick " + chopstickNum;
    }
}
