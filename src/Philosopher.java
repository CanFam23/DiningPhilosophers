package src;

public class Philosopher implements Runnable {
    private final int eatTime = 1; // Make static? or different for each philosopher?

    private final int thinkTime = 1; // Make static? or different for each philosopher?

    private boolean running = false;

    private State state;

    /** Could be useful for debugging */
    private int philNum;

    public Philosopher(int philNum) {
        this.state = State.Hungry;
        this.philNum = philNum;
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {

    }

    public void stop(){
        running = false;
    }

    public void grabChopstick(){
        return;
    }

    public void eat(){
        return;
    }

    public void think(){
        return;
    }
}
